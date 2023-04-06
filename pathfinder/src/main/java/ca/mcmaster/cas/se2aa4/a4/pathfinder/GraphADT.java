package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.IdAlreadyExists;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.NoSuchNodeExists;

import java.util.*;
import java.util.stream.Collectors;

public class GraphADT {
    private final Map<Node, List<Edge>> adjacencyList = new HashMap<>();
    private final boolean directed;

    public GraphADT(boolean directed) {
        this.directed = directed;
    }

    public void addNode(Integer id) throws IdAlreadyExists {
        if (adjacencyList.keySet().stream().anyMatch(element -> element.getId() == id))
            throw new IdAlreadyExists("An node with id "+id+" already exists in the graph.");
        adjacencyList.putIfAbsent(new Node(id), new ArrayList<>());
    }

    public void removeNode(Integer id) {
        Node node = new Node(id);
        // remove all incoming edges
        adjacencyList.values().forEach(e -> e.removeIf(edge -> edge.getEndNode() == node));
        // remove all outgoing edges
        adjacencyList.remove(new Node(id));
    }

    public void addEdge(Integer n1, Integer n2, Double weight, Integer id) throws NoSuchNodeExists, IdAlreadyExists {
        Node node1 = getNode(n1);
        Node node2 = getNode(n2);
        if (node1 == null) throw new NoSuchNodeExists("Node id "+n1+" does not exist.");
        if (node2 == null) throw new NoSuchNodeExists("Node id "+n2+" does not exist.");
        if (adjacencyList.values().stream().flatMap(List::stream).anyMatch(element -> Objects.equals(element.getId(), id)))
            throw new IdAlreadyExists("An edge with id "+id+" already exists in the graph.");

        Edge edge = new Edge(node1, node2, weight, id);
        adjacencyList.get(node1).add(edge);

        if (!directed) {
            Edge oppositeEdge = new Edge(node2, node1, weight, id);
            adjacencyList.get(node2).add(oppositeEdge);
        }
    }

    public void addEdge(Integer n1, Integer n2, Integer weight, Integer id) throws NoSuchNodeExists, IdAlreadyExists {
        addEdge(n1, n2, weight*1.0, id);
    }

    public void removeEdge(Integer id) {
        adjacencyList.values().forEach(e -> e.removeIf(edge -> Objects.equals(edge.getId(), id)));
    }

    public Node getNode(Integer id) {
        return adjacencyList.keySet().stream().filter(node -> Objects.equals(node.getId(), id)).findFirst().orElse(null);
    }

    public List<Node> getNodes() {
        return adjacencyList.keySet().stream().toList();
    }

    public List<Edge> getEdge(Integer id) {
        return adjacencyList.values().stream()
                .flatMap(Collection::stream)
                .filter(obj -> Objects.equals(obj.getId(), id))
                .collect(Collectors.toList());
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        adjacencyList.values().forEach(edges::addAll);
        return edges;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("GraphADT{\n" +
                "adjacencyList=\n");
        for (Map.Entry<Node, List<Edge>> entry : adjacencyList.entrySet()) {
            Node node = entry.getKey();
            List<Edge> edges = entry.getValue();
            string.append(node.toString()).append(" -> ").append(edges).append("\n");
        }

        string.append(", directed=").append(directed).append('}');
        return string.toString();
    }
}
