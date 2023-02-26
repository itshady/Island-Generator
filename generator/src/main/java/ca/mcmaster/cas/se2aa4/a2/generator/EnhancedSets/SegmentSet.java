package ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets;

import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Segment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SegmentSet implements GeometrySet<Segment> {
    private final Map<Integer, Segment> segments = new HashMap<>();
    private final Map<Segment, Integer> segmentsInverse = new HashMap<>();
    Integer id = 0;

    public SegmentSet() {}

    public Integer add(Segment segment) {
        if (contains(segment)) return segmentsInverse.get(segment);
        segments.put(id, segment);
        segmentsInverse.put(segment, id);
        return id++;
    }

    public boolean contains(Segment segment) {
        return segments.containsValue(segment);
    }

    public Segment get(Integer id) {
        return segments.get(id);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator where the key is the segment id and the values are the Segments.
     */
    @Override
    public Iterator iterator() {
        return segments.entrySet().iterator();
    }
}
