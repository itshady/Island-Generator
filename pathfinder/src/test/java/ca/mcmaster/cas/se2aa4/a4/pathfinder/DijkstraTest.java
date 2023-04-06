package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.IdAlreadyExists;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.NoSuchNodeExists;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DijkstraTest {
    @Test
    void dijkstras() {
        // This graph is from an example in 2C03 slideset 4
        GraphADT graph = new GraphADT(false);
        try {
            graph.addNode(0);
            graph.addNode(1);
            graph.addNode(2);
            graph.addNode(3);
            graph.addNode(4);
            graph.addNode(5);
            graph.addNode(6);
            graph.addNode(7);
            graph.addNode(8);
            graph.addNode(9);

            graph.addEdge(0, 3, 9, 0);
            graph.addEdge(0, 7, 12, 1);
            graph.addEdge(0, 4, 13, 2);
            graph.addEdge(0, 9, 11, 3);
            graph.addEdge(7, 2, 7, 4);
            graph.addEdge(2, 3, 5, 5);
            graph.addEdge(3, 8, 1, 6);
            graph.addEdge(8, 1, 4, 7);
            graph.addEdge(8, 5, 7, 8);
            graph.addEdge(1, 5, 2, 9);
            graph.addEdge(5, 9, 8, 10);
            graph.addEdge(5, 6, 3, 11);
            graph.addEdge(6, 9, 4, 12);
        } catch (NoSuchNodeExists | IdAlreadyExists e) {
            System.out.println(e.getMessage());
        }

        Pathfinder dijkstra = new Dijkstra();
        List<Edge> path = dijkstra.getShortestPath(graph, graph.getNode(3), graph.getNode(0));
        assertEquals(1, path.size());
        assertTrue(graph.getEdge(0).contains(path.get(0)));

        path = dijkstra.getShortestPath(graph, graph.getNode(3), graph.getNode(9));
        List<List<Edge>> actualShortestPath = new ArrayList<>();
        actualShortestPath.add(graph.getEdge(6));
        actualShortestPath.add(graph.getEdge(7));
        actualShortestPath.add(graph.getEdge(9));
        actualShortestPath.add(graph.getEdge(11));
        actualShortestPath.add(graph.getEdge(12));

        assertEquals(actualShortestPath.size(), path.size());
        for (int i=0; i<actualShortestPath.size(); i++) {
            assertTrue(actualShortestPath.get(i).contains(path.get(i)));
        }
    }
}