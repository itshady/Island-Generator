package ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets;

import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.PropertyHandler;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Vertex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexSetTest {
    @Test
    void add() {
        VertexSet vertexSet = new VertexSet();
        Vertex v1 = new Vertex(1.0, 1.0);
        Vertex v2 = new Vertex(2.0, 2.0);
        Vertex v1_dup = new Vertex(1.0, 1.0);

        assertEquals(0,vertexSet.add(v1));
        assertEquals(1,vertexSet.add(v2));
        assertEquals(0,vertexSet.add(v1_dup));

        int counter = 0;
        for (Vertex v : vertexSet) counter++;
        assertEquals(2, counter);
    }

    @Test
    void get() {
        // context
        VertexSet vertexSet = new VertexSet();
        Vertex v1 = new Vertex(1.0, 1.0);
        Vertex v2 = new Vertex(2.0, 2.0);
        vertexSet.add(v1);
        vertexSet.add(v2);

        // test
        assertEquals(new Vertex(1.0,1.0), vertexSet.get(0));
        assertEquals(vertexSet.get(0), vertexSet.get(new Coordinate(1.0,1.0)));
    }

    @Test
    void contains() {
        // context
        VertexSet vertexSet = new VertexSet();
        Vertex v1 = new Vertex(1.0, 1.0);
        Vertex v2 = new Vertex(2.0, 3.0);
        vertexSet.add(v1);
        vertexSet.add(v2);

        // test
        assertTrue(vertexSet.contains(new Vertex(2.0, 3.0)));
        assertFalse(vertexSet.contains(new Vertex(3.0, 2.0)));
    }
}