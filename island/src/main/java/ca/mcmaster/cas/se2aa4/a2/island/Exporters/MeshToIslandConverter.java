package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Tile;

import java.util.ArrayList;
import java.util.List;

public class MeshToIslandConverter {
    private Mesh mesh;

    public MeshToIslandConverter(Mesh mesh) {
        this.mesh = mesh;
    }

    public Island process() {
        Island island = new Island();

        island.register(convert(mesh.polygons), convert(mesh.segments), convert(mesh.vertices));

        return island;
    }

    private List<Tile> convert(PolygonSet polygons) {
        List<Tile> tiles = new ArrayList<>();
        for (Polygon polygon : polygons) {
            tiles.add(new Tile(polygon));
        }
        return tiles;
    }

    private List<Segment> convert(SegmentSet segments) {
        List<Segment> segmentsList = new ArrayList<>();
        for (Segment segment : segments) {
            segmentsList.add(segment);
        }
        return segmentsList;
    }

    private List<Vertex> convert(VertexSet vertices) {
        List<Vertex> vertexList = new ArrayList<>();
        for (Vertex vertex : vertices) {
            vertexList.add(vertex);
        }
        return vertexList;
    }
}
