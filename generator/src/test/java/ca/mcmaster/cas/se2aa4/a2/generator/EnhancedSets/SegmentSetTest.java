package ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets;

import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Vertex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SegmentSetTest {

    @Test
    void add() {
        // context
        SegmentSet segmentSet = new SegmentSet();
        Segment s1 = new Segment(new Vertex(1.0,1.0), new Vertex(2.0,2.0));
        Segment s1_dup = new Segment(new Vertex(2.0,2.0), new Vertex(1.0,1.0));
        Segment s2 = new Segment(new Vertex(2.0,2.0), new Vertex(3.0,3.0));

        // action and assertion
        assertEquals(0, segmentSet.add(s1));
        assertEquals(1, segmentSet.add(s2));
        assertEquals(0, segmentSet.add(s1_dup));

        int counter = 0;
        for (Segment s : segmentSet) counter++;
        assertEquals(2, counter);
    }

    @Test
    void contains() {
        // context
        SegmentSet segmentSet = new SegmentSet();
        Segment s1 = new Segment(new Vertex(1.0,1.0), new Vertex(2.0,2.0));
        Segment s2 = new Segment(new Vertex(2.0,2.0), new Vertex(3.0,3.0));

        // action
        segmentSet.add(s1);
        segmentSet.add(s2);

        // assertion
        assertTrue(segmentSet.contains(new Segment(new Vertex(1.0,1.0), new Vertex(2.0,2.0))));
        assertTrue(segmentSet.contains(new Segment(new Vertex(2.0,2.0), new Vertex(3.0,3.0))));
    }

    @Test
    void get() {
    }

    @Test
    void iterator() {
    }
}