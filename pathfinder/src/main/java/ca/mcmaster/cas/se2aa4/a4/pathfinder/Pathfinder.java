package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import java.util.List;

public interface Pathfinder {
    List<Edge> getShortestPath(GraphADT graph, Node node1, Node node2);
}
