package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.*;

public class Dijkstra implements Pathfinder {
    private final Map<Node, Node> path = new HashMap<>();
    private final Map<Node, Double> cost = new HashMap<>();

    static class NodeCostComparator implements Comparator<Node> {
        @Override
        public int compare(Node n1, Node n2) {
            return Double.compare(n1.getCost(), n2.getCost());
        }
    }

    @Override
    public List<Edge> getShortestPath(GraphADT graph, Node startNode, Node destinationNode) {
        dijkstras(graph, startNode);
        return getPath(graph, startNode, destinationNode);
    }

    @Override
    public Map<Node, List<Edge>> getShortestPaths(GraphADT graph, Node source) {
        dijkstras(graph, source);
        Map<Node, List<Edge>> paths = new HashMap<>();
        for (Node node : graph.getNodes()) {
            paths.put(node, getPath(graph, source, node));
        }
        return paths;
    }

    private void dijkstras(GraphADT graph, Node source) {
        // reset all node status
        graph.getAdjacencyList().keySet().forEach(e -> e.setStatus(Node.NodeStatus.UNVISITED));
        // initialize path and cost
        graph.getAdjacencyList().keySet().forEach(e -> path.putIfAbsent(e, null));
        graph.getAdjacencyList().keySet().forEach(e -> cost.putIfAbsent(e, Double.MAX_VALUE));
        path.put(source, source);
        cost.put(source, 0.0);
        PriorityQueue<Node> Q = new PriorityQueue<>(new NodeCostComparator());
        // set source to cost 0 and add to Q
        source.setCost(0.0);
        Q.add(source);

        while(!Q.isEmpty()) {
            Node m = Q.poll();
            m.setStatus(Node.NodeStatus.VISITING);
            for (Edge e : graph.getAdjacencyList().get(m)) {
                Node n = e.getEndNode();
                if (n.getStatus() == Node.NodeStatus.VISITED) continue;
                if (cost.get(m) + e.getWeight() < cost.get(n)) {
                    path.put(n, m);
                    cost.put(n, e.getWeight() + cost.get(m));
                    n.setCost(cost.get(n));
                    Q.add(n);
                }
            }
            m.setStatus(Node.NodeStatus.VISITED);
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
