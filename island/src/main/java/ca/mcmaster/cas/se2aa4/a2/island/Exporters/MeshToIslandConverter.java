package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.ArrayList;
import java.util.List;

public class MeshToIslandConverter {
    private final Mesh mesh;

    private final List<VertexDecorator> vertexDecorators = new ArrayList<>();
    private final List<Border> borders = new ArrayList<>();
    private final List<Tile> tiles = new ArrayList<>();

    public MeshToIslandConverter(Mesh mesh) {
        this.mesh = mesh;
    }

    public Island process() {
        Island island = new Island();
        convert(mesh.vertices);
        convert(mesh.segments);
        convert(mesh.polygons);
        island.register(vertexDecorators, borders, tiles);
        return island;
    }

    private void convert(PolygonSet polygons) {
        for (Polygon polygon : polygons) {
            tiles.add(
                    Tile.newBuilder()
                            .addPolygon(polygon)
                            .addBorders(getAssociatedBorders(polygon.getSegmentList()))
                            .build()
            );
        }
    }

    private List<Border> getAssociatedBorders(List<Segment> segments) {
        List<Border> bordersList = new ArrayList<>();
        for (Segment segment : segments) {
            bordersList.add(
                borders.get(segment.getId())
            );
        }
        return bordersList;
    }
    private void convert(SegmentSet segments) {
        for (Segment segment : segments) {
            VertexDecorator v1 = vertexDecorators.get(segment.getV1().getId());
            VertexDecorator v2 = vertexDecorators.get(segment.getV2().getId());

            borders.add(
                    Border.newBuilder().addSegment(segment).addV1(v1).addV2(v2).build()
            );
        }
    }

    private void convert(VertexSet vertices) {
        for (Vertex vertex : vertices) {
            vertexDecorators.add(
                    VertexDecorator.newBuilder().addVertex(vertex).build()
            );
        }
    }
}
