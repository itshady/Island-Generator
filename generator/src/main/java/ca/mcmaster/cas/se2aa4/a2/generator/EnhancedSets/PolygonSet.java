package ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets;

import Geometries.Polygon;
import java.util.*;

public class PolygonSet implements GeometrySet<Polygon>, Iterable<Polygon> {
    private final Map<Integer, Polygon> polygons = new HashMap<>();
    private final Map<Polygon, Integer> polygonsInverse = new HashMap<>();
    Integer id = 0;

    /**
     * Must maintain Set property (any equal polygons can't be in set together)
     *
     * @param polygon : the given Geometry to add
     * @return Integer: id of segment
     */
    @Override
    public Integer add(Polygon polygon) {
        if (contains(polygon)) return polygonsInverse.get(polygon);
        polygons.put(id, polygon);
        polygonsInverse.put(polygon, id);
        return id++;
    }

    /**
     * Checks if the Set contains the given Geometry
     *
     * @param polygon : the given Geometry to check
     * @return Boolean: true if it does, false if not
     */
    @Override
    public boolean contains(Polygon polygon) {
        return polygons.containsValue(polygon);
    }

    /**
     * Gets the Geometry E from the Set given its id
     *
     * @param id : the id of the Geometry
     * @return Geometry: Returns the geometry if it exists, else null.
     */
    @Override
    public Polygon get(Integer id) {
        return polygons.get(id);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<Polygon> iterator() {
        return polygons.values().iterator();
    }

    public Map<Integer, Polygon> getPolygons() {
        return Collections.unmodifiableMap(polygons);
    }
}
