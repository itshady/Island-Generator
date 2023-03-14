package ca.mcmaster.cas.se2aa4.a2.island.Containers;

import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Tile;
import org.locationtech.jts.geom.Polygon;

import java.util.List;
import java.util.Map;

public class Island {
    private List<Vertex> vertices;
    private List<Segment> segments;
    private List<Tile> tiles;

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void register(List<Tile> polygons, List<Segment> segments, List<Vertex> vertices) {
        tiles = polygons;
        this.segments = segments;
        this.vertices = vertices;
    }
}
