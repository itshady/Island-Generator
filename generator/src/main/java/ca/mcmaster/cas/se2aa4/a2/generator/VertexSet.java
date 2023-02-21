package ca.mcmaster.cas.se2aa4.a2.generator;

import java.util.HashMap;
import java.util.Map;

public class VertexSet {
    private final Map<Coordinate, Vertex> coords = new HashMap<>();
    private final Map<Coordinate, Integer> ids = new HashMap<>();
    int idCounter = 0;

    public VertexSet(double precision) {
        Coordinate.precision = precision;
    }

    public Integer add(Vertex vertex) {
        Coordinate coord = new Coordinate(vertex.getX(), vertex.getY());
        if (contains(vertex)) return ids.get(coord);
        coords.put(coord, vertex);
        ids.put(coord, idCounter);
        idCounter++;
        return idCounter-1;
    }

    public Vertex getVertex(Coordinate coord) {
        return coords.get(new Coordinate(coord.getX(), coord.getY()));
    }

    public boolean contains(Vertex vertex) {
        return coords.containsValue(vertex);
    }

    public boolean contains(Integer x, Integer y) {
        Coordinate coordinate = new Coordinate(x, y);
        return coords.containsKey(coordinate);
    }
}
