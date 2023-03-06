package ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolygonSetTest {

    @Test
    void add() {
        // context
        PolygonSet polygonSet = new PolygonSet();
        List<Segment> segments1 = new ArrayList<>();
        segments1.add(new Segment(new Vertex(1.0,1.0), new Vertex(2.0, 2.0)));
        segments1.add(new Segment(new Vertex(2.0,2.0), new Vertex(3.0, 3.0)));
        segments1.add(new Segment(new Vertex(3.0,3.0), new Vertex(1.0, 1.0)));
        Polygon p1 = new Polygon(segments1);

        List<Segment> segments2 = new ArrayList<>();
        segments2.add(new Segment(new Vertex(1.0,2.0), new Vertex(2.0, 3.0)));
        segments2.add(new Segment(new Vertex(2.0,3.0), new Vertex(3.0, 4.0)));
        segments2.add(new Segment(new Vertex(3.0,4.0), new Vertex(1.0, 2.0)));
        Polygon p2 = new Polygon(segments2);

        List<Segment> segments3 = new ArrayList<>();
        segments3.add(new Segment(new Vertex(1.0,2.0), new Vertex(2.0, 3.0)));
        segments3.add(new Segment(new Vertex(2.0,3.0), new Vertex(3.0, 4.0)));
        segments3.add(new Segment(new Vertex(3.0,4.0), new Vertex(1.0, 2.0)));
        Polygon p2_dup = new Polygon(segments3);

        // action and assertion
        assertEquals(0, polygonSet.add(p1));
        assertEquals(1, polygonSet.add(p2));
        assertEquals(1, polygonSet.add(p2_dup));

        int counter = 0;
        for (Polygon p : polygonSet) counter++;
        assertEquals(2, counter);
    }

    @Test
    void contains() {
        // context
        PolygonSet polygonSet = new PolygonSet();
        List<Segment> segments1 = new ArrayList<>();
        segments1.add(new Segment(new Vertex(1.0,1.0), new Vertex(2.0, 2.0)));
        segments1.add(new Segment(new Vertex(2.0,2.0), new Vertex(3.0, 3.0)));
        segments1.add(new Segment(new Vertex(3.0,3.0), new Vertex(1.0, 1.0)));
        Polygon p1 = new Polygon(segments1);

        List<Segment> segments2 = new ArrayList<>();
        segments2.add(new Segment(new Vertex(1.0,2.0), new Vertex(2.0, 3.0)));
        segments2.add(new Segment(new Vertex(2.0,3.0), new Vertex(3.0, 4.0)));
        segments2.add(new Segment(new Vertex(3.0,4.0), new Vertex(1.0, 2.0)));
        Polygon p2 = new Polygon(segments2);

        polygonSet.add(p1);

        // action
        boolean containsP1 = polygonSet.contains(p1);
        boolean containsP2 = polygonSet.contains(p2);

        // assertion
        assertTrue(containsP1);
        assertFalse(containsP2);
    }

    @Test
    void get() {
        // context
        PolygonSet polygonSet = new PolygonSet();
        List<Segment> segments1 = new ArrayList<>();
        segments1.add(new Segment(new Vertex(1.0,1.0), new Vertex(2.0, 2.0)));
        segments1.add(new Segment(new Vertex(2.0,2.0), new Vertex(3.0, 3.0)));
        segments1.add(new Segment(new Vertex(3.0,3.0), new Vertex(1.0, 1.0)));
        Polygon p1 = new Polygon(segments1);

        List<Segment> segments2 = new ArrayList<>();
        segments2.add(new Segment(new Vertex(1.0,2.0), new Vertex(2.0, 3.0)));
        segments2.add(new Segment(new Vertex(2.0,3.0), new Vertex(3.0, 4.0)));
        segments2.add(new Segment(new Vertex(3.0,4.0), new Vertex(1.0, 2.0)));
        Polygon p2 = new Polygon(segments2);

        polygonSet.add(p1);
        polygonSet.add(p2);

        // action
        Polygon getP1 = polygonSet.get(0);
        Polygon getP2 = polygonSet.get(1);

        // assertion
        assertEquals(p1, getP1);
        assertEquals(p2, getP2);
    }
}