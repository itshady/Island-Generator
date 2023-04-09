package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.List;
import java.util.Map;

public interface Pathfinder {
    /**
     * Given a graph, get the shortest path between a start and destination node.
     * @param graph
     * @param startNode
     * @param destinationNode
     * @return The shortest path
     */
    List<Edge> getShortestPath(GraphADT graph, Node startNode, Node destinationNode);

    /**
     * Given a graph, get all shortest paths between a source node and all other nodes.
     * @param graph
     * @param source
     * @return Map of all shortest paths
     */
    Map<Node, List<Edge>> getShortestPaths(GraphADT graph, Node source);
}
