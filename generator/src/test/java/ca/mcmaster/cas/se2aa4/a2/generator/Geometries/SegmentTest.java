package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    @Test
    void getVertices() {
        Vertex v1 = new Vertex(1.0, 1.0);
        Vertex v2 = new Vertex(2.0, 2.0);
        Segment segment = new Segment(v1,v2);
        assertEquals(v1, segment.getV1());
        assertEquals(v2, segment.getV2());
    }

    @Test
    void getColor() {
        Vertex v1 = new Vertex(1.0, 1.0, new Color(0,0,100));
        Vertex v2 = new Vertex(2.0, 2.0, new Color(200,200,200));
        Segment segment = new Segment(v1,v2);
        assertEquals(new Color(100,100,150), segment.getColor());
    }

    @Test
    void generateSegment() {
        Vertex v1 = new Vertex(1.0, 1.0);
        Vertex v2 = new Vertex(2.0, 2.0);
        v1.setId(0);
        v2.setId(1);
        Segment segment = new Segment(v1,v2);
        assertNull(segment.getSegment());
        segment.generateSegment();
        assertNotNull(segment.getSegment());
    }

    @Test
    void testEquals() {
        Vertex v1 = new Vertex(1.0, 1.0);
        Vertex v2 = new Vertex(2.0, 2.0);

        Segment segment = new Segment(v1,v2);
        Segment segment_dup =  new Segment(v2,v1);
        assertEquals(segment, segment_dup);
    }
}