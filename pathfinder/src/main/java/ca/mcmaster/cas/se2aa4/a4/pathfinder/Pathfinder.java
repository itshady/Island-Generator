package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.List;
import java.util.Map;

public interface Pathfinder {
    List<Edge> getShortestPath(GraphADT graph, Node node1, Node node2);

    Map<Node, List<Edge>> getShortestPaths(GraphADT graph, Node source);
}
