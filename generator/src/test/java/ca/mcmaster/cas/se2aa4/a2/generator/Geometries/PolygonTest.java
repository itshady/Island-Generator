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
        System.out.println("moreThanTwoSegments Starting.");
        Vertex v1 = new Vertex(1.0,7.0);
        Vertex v2 = new Vertex(3.0,6.0);
        List<Segment> segments = new ArrayList<>();
        segments.add(new Segment(v1,v2));
        segments.add(new Segment(v2,v1));
        Throwable exception = assertThrows(IllegalCallerException.class, () -> new Polygon(segments));
        assertEquals("Must have more than 2 segments. Given " + segments.size(), exception.getMessage());
        System.out.println("moreThanTwoSegments Ending.");
    }

    @Test
    void setCentroid() {
        System.out.println("setCentroid Starting.");
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
        polygon.setCentroid(centroid);
        assertNotNull(polygon.getCentroid());
        System.out.println("setCentroid Ending.");
    }

    @Test
    void generatePolygon() {
        System.out.println("generatePolygon Starting.");
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
        polygon.setCentroid(centroid);
        polygon.generatePolygon();
        assertNotNull(polygon.getPolygon());
        System.out.println("generatePolygon Ending.");
    }

}