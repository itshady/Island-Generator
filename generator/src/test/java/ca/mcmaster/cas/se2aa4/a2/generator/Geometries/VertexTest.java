package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VertexTest {

    @Test
    void getVertex() {
        Vertex vertex = new Vertex(1.0,1.0);
        assertNotNull(vertex.getVertex());
    }

    @Test
    void testEquals() {
        Vertex vertex = new Vertex(1.0,1.0);
        Vertex vertex_dup = new Vertex(1.00001,1.00001);

        assertEquals(vertex, vertex_dup);
    }
}