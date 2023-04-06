package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.NegativeCycleExists;

import java.util.*;

public class BellmanFord implements Pathfinder {
    private final Map<Node, Node> path = new HashMap<>();
    private final Map<Node, Double> cost = new HashMap<>();
    private boolean negativeCycle = false;

    public boolean hasNegativeCycle() {
        return negativeCycle;
    }

    @Override
    public List<Edge> getShortestPath(GraphADT graph, Node startNode, Node destinationNode) {
        try {
            bellmanford(graph, startNode);
            negativeCycle = false;
        } catch (NegativeCycleExists e) {
            negativeCycle = true;
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
        return getPath(graph, startNode, destinationNode);
    }

    @Override
    public Map<Node, List<Edge>> getShortestPaths(GraphADT graph, Node source) {
        try {
            bellmanford(graph, source);
            negativeCycle = false;
        } catch (NegativeCycleExists e) {
            negativeCycle = true;
            System.out.println(e.getMessage());
            return new HashMap<>();
        }
        Map<Node, List<Edge>> paths = new HashMap<>();
        for (Node node : graph.getNodes()) {
            paths.put(node, getPath(graph, source, node));
        }
        return paths;
    }

    /**
     * Bellman-Ford algorithm to set all shortest paths
     * @param graph
     * @param source
     * @return true if there is no negative cycle and false if there is
     */
    private void bellmanford(GraphADT graph, Node source) throws NegativeCycleExists {
        // initialize path and cost
        graph.getAdjacencyList().keySet().forEach(e -> path.putIfAbsent(e, null));
        graph.getAdjacencyList().keySet().forEach(e -> cost.putIfAbsent(e, Double.MAX_VALUE));
        // initialize start node
        path.put(source, source);
        cost.put(source, 0.0);

        // Relax all edges |N| - 1 times.
        for (int i=0; i < graph.getNodes().size() - 1; i++) {
            for(Edge edge : graph.getEdges()) {
                Node m = edge.getStartNode();
                Node n = edge.getEndNode();
                if (cost.get(m) + edge.getWeight() < cost.get(n)) {
                    path.put(n, m);
                    cost.put(n, edge.getWeight() + cost.get(m));
                }
            }
        }

        // check for negative cycle
        for(Edge edge : graph.getEdges()) {
            Node m = edge.getStartNode();
            Node n = edge.getEndNode();
            if (cost.get(m) + edge.getWeight() < cost.get(n)) {
                throw new NegativeCycleExists("There are negative edge weights in the cycle.");
            }
        }
    }

    private List<Edge> getPath(GraphADT graph, Node source, Node dest) {
        Node currNode = dest;
        LinkedList<Edge> shortestPath = new LinkedList<>();
        while (path.get(currNode) != null && currNode != source) {
            Node parent = path.get(currNode);
            Node finalCurrNode = currNode;
            Edge edge = graph.getEdges().stream().filter(e -> e.getStartNode() == parent && e.getEndNode() == finalCurrNode).findFirst().orElse(null);
            shortestPath.addFirst(edge);
            currNode = parent;
        }
        return shortestPath.stream().toList();
    }
}
