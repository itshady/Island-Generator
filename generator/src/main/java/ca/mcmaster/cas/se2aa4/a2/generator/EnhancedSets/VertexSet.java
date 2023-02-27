package ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets;

import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Vertex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VertexSet implements GeometrySet<Vertex>, Iterable<Vertex> {
    private final Map<Coordinate, Vertex> coords = new HashMap<>();
    private final Map<Coordinate, Integer> ids = new HashMap<>();
    private final Map<Integer, Vertex> idToVertex = new HashMap<>();
    int idCounter = 0;

    public VertexSet() {}

    /**
     * Must maintain Set property (any equal vertices can't be in set together)
     * @param vertex: the given Geometry to add
     * @return Integer: id of vertex
     */
    public Integer add(Vertex vertex) {
        Coordinate coord = new Coordinate(vertex.getX(), vertex.getY());
        if (contains(vertex)) return ids.get(coord);
        coords.put(coord, vertex);
        ids.put(coord, idCounter);
        idToVertex.put(idCounter, vertex);
        return idCounter++;
    }

    /**
     * Gets the Geometry E from the Set given its Coordinates
     * @param coord: the Coordinate of the Geometry
     * @return Geometry: Returns the geometry if it exists, else null.
     */
    public Vertex get(Coordinate coord) {
        return coords.get(new Coordinate(coord.getX(), coord.getY()));
    }

    /**
     * Gets the Geometry E from the Set given its id
     * @param id: the id of the Geometry
     * @return Geometry: Returns the geometry if it exists, else null.
     */
    public Vertex get(Integer id) {
        return idToVertex.get(id);
    }

    /**
     * Checks if the Set contains the given Geometry
     * @param vertex: the given Geometry to check
     * @return Boolean: true if it does, false if not
     */
    public boolean contains(Vertex vertex) {
        return coords.containsValue(vertex);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator where the key is the vertex id and the values are the Vertices.
     */
    @Override
    public Iterator<Vertex> iterator() {
        return idToVertex.values().iterator();
    }
}
