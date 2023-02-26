package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VertexSet implements Iterable {
    private final Map<Coordinate, Vertex> coords = new HashMap<>();
    private final Map<Coordinate, Integer> ids = new HashMap<>();
    private final Map<Integer, Vertex> idToVertex = new HashMap<>();
    int idCounter = 0;

    public VertexSet(double precision) {
        Coordinate.precision = precision;
    }

    // returns the id of the vertex passed in (either existing vertex or newly created)
    public Integer add(Vertex vertex) {
        Coordinate coord = new Coordinate(vertex.getX(), vertex.getY());
        if (contains(vertex)) return ids.get(coord);
        coords.put(coord, vertex);
        ids.put(coord, idCounter);
        idToVertex.put(idCounter, vertex);
        return idCounter++;
    }

    public Vertex getVertex(Coordinate coord) {
        return coords.get(new Coordinate(coord.getX(), coord.getY()));
    }

    public Vertex getVertex(Integer id) {
        return idToVertex.get(id);
    }

    public boolean contains(Vertex vertex) {
        return coords.containsValue(vertex);
    }

    public boolean contains(Integer x, Integer y) {
        Coordinate coordinate = new Coordinate(x, y);
        return coords.containsKey(coordinate);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator where the key is the vertex id and the values are the Vertices.
     */
    @Override
    public Iterator iterator() {
        return idToVertex.entrySet().iterator();
    }
}
