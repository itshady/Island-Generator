package ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters;

import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Segment;
import Geometries.Vertex;
import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.ArrayList;
import java.util.List;

public class IslandToMeshConverter {
    public Mesh process(Island island) {
        Mesh mesh = new Mesh();
        mesh.vertices = new VertexSet(convertToVertices(island.getVertexDecorators()));
        mesh.segments = new SegmentSet(convertToSegments(island.getBorders()));

        mesh.polygons = convertToPolygons(island.getTiles());
        return mesh;
    }

    private List<Vertex> convertToVertices(List<VertexDecorator> decorators) {
        List<Vertex> vertices = new ArrayList<>();
        for (VertexDecorator decorator : decorators) {
            vertices.add(decorator.getVertex());
        }
        return vertices;
    }

    private List<Segment> convertToSegments(List<Border> borders) {
        List<Segment> segments = new ArrayList<>();
        for (Border border : borders) {
            border.enhanceBorder();
            segments.add(border.getSegment());
        }
        return segments;
    }

    private PolygonSet convertToPolygons(List<Tile> tiles) {
        PolygonSet set = new PolygonSet();
        for (Tile tile : tiles) {
            tile.enhancePolygon();
            set.add(tile.getPolygon());
        }
        return set;
    }
}
