package ca.mcmaster.cas.se2aa4.a4.pathfinder;

import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.NoSuchNodeExists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GraphADTTest {
    @Test
    void addAndRemoveNodes() {
        GraphADT graph = new GraphADT(false);
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        assertEquals(4, graph.getNodes().size());

        graph.removeNode(1);

        assertEquals(3, graph.getNodes().size());
        for (Node n : graph.getNodes()) {
            assertNotEquals(1, n.getId());
        }
    }

    @Test
    void addAndRemoveEdges_UnDiGraph() {
        GraphADT graph = new GraphADT(false);
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        try {
            graph.addEdge(0, 1, 1, 0);
            graph.addEdge(1, 2, 1, 1);
        } catch (NoSuchNodeExists e) {
            System.out.println(e.getMessage());
        }

        assertEquals(4, graph.getEdges().size());

        graph.removeEdge(1);

        assertEquals(2, graph.getEdges().size());
    }

    @Test
    void addAndRemoveEdges_DiGraph() {
        GraphADT graph = new GraphADT(true);
        graph.addNode(0);
        graph.addNode(1);
        graph.addNode(2);
        graph.addNode(3);

        try {
            graph.addEdge(0, 1, 1, 0);
            graph.addEdge(1, 2, 1, 1);
        } catch (NoSuchNodeExists e) {
            System.out.println(e.getMessage());
        }

        assertEquals(2, graph.getEdges().size());

        graph.removeEdge(1);

        assertEquals(1, graph.getEdges().size());
    }
}