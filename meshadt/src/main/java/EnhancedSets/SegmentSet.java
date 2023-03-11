package EnhancedSets;

import Geometries.Segment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SegmentSet implements GeometrySet<Segment>, Iterable<Segment> {
    private final Map<Integer, Segment> segments = new HashMap<>();
    private final Map<Segment, Integer> segmentsInverse = new HashMap<>();
    Integer id = 0;

    public SegmentSet() {}

    /**
     * Must maintain Set property (any equal segments can't be in set together)
     *
     * @param segment : the given Geometry to add
     * @return Integer: id of segment
     */
    @Override
    public Integer add(Segment segment) {
        if (contains(segment)) return segmentsInverse.get(segment);
        segment.setId(id);
        segments.put(id, segment);
        segmentsInverse.put(segment, id);
        return id++;
    }

    /**
     * Checks if the Set contains the given Geometry
     *
     * @param segment : the given Geometry to check
     * @return Boolean: true if it does, false if not
     */
    @Override
    public boolean contains(Segment segment) {
        return segments.containsValue(segment);
    }

    /**
     * Gets the Geometry E from the Set given its id
     *
     * @param id : the id of the Geometry
     * @return Geometry: Returns the geometry if it exists, else null.
     */
    @Override
    public Segment get(Integer id) {
        return segments.get(id);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator where the key is the segment id and the values are the Segments.
     */
    @Override
    public Iterator<Segment> iterator() {
        return segments.values().iterator();
    }
}
