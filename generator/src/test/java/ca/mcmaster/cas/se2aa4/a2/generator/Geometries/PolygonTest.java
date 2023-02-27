package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PolygonTest {

    @BeforeAll
    static void setup() {
        System.out.println("Polygon Tests Starting.");
    }

    @AfterAll
    static void teardown() {
        System.out.println("Polygon Tests Ending.");
    }

    @Test
    void moreThanTwoSegments() {
        // assert no polygon has more than 2 segments
        // context
        Vertex v1 = new Vertex(1.0,7.0);
        Vertex v2 = new Vertex(3.0,6.0);
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(v1,v2));
        segments.add(new Segment(v2,v1));

        // action and assertion
        Throwable exception = assertThrows(IllegalCallerException.class, () -> new Polygon(segments));
        assertEquals("Must have more than 2 segments. Given " + segments.size(), exception.getMessage());
    }

    @Test
    void setCentroid() {
        // assert centroid can be set
        // context
        Vertex v1 = new Vertex(1.0,7.0);
        Vertex v2 = new Vertex(3.0,6.0);
        Vertex v3 = new Vertex(2.0,3.0);
        Centroid centroid = new Centroid(2.0, 2.0);
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(v1,v2));
        segments.add(new Segment(v2,v3));
        segments.add(new Segment(v3,v1));
        Polygon polygon = new Polygon(segments);
        assertNull(polygon.getCentroid());

        // action
        polygon.setCentroid(centroid);

        // assertion
        assertNotNull(polygon.getCentroid());
    }

    @Test
    void generatePolygon() {
        // context
        Vertex v1 = new Vertex(1.0,7.0);
        Vertex v2 = new Vertex(3.0,6.0);
        Vertex v3 = new Vertex(2.0,3.0);
        Centroid centroid = new Centroid(2.0, 2.0);
        v1.setId(0);
        v2.setId(1);
        v3.setId(2);
        centroid.setId(3);
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(v1,v2));
        segments.add(new Segment(v2,v3));
        segments.add(new Segment(v3,v1));
        for (int i = 0; i<3; i++){
            segments.get(i).setId(i);
        }
        Polygon polygon = new Polygon(segments);

        // action
        polygon.setCentroid(centroid);
        polygon.generatePolygon();

        // assertion
        assertNotNull(polygon.getPolygon());
    }

}