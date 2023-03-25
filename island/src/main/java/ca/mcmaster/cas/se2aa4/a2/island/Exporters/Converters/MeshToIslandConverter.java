package ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters;

import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.ArrayList;
import java.util.List;

public class MeshToIslandConverter {
    private final List<VertexDecorator> vertexDecorators = new ArrayList<>();
    private final List<Border> borders = new ArrayList<>();
    private final List<Tile> tiles = new ArrayList<>();

    /**
     * Converts all MeshADT type vertices, segments, and polygons into Island type equivalents
     * @param mesh, MeshADT
     * @return Island
     */
    public Island process(Mesh mesh) {
        Island island = new Island();
        convert(mesh.vertices);
        convert(mesh.segments);
        convert(mesh.polygons);
        island.register(vertexDecorators, borders, tiles);
        return island;
    }

    /**
     * Collection of Polygons are converted to Tiles
     * @param polygons, PolygonSet -> EnhancedSet of Polygons to prevent duplicates
     */
    private void convert(PolygonSet polygons) {
        for (Polygon polygon : polygons) {
            tiles.add(
                Tile.newBuilder()
                    .addPolygon(polygon)
                    .addBorders(getAssociatedBorders(polygon.getSegmentList()))
                    .addCentroid(vertexDecorators.get(polygon.getCentroidId()))
                    .build()
            );
        }
    }

    /**
     * Convert Segments into their Island Border equivalents for the Tiles
     * @param segments, List<Segment>
     * @return List<Border>
     */
    private List<Border> getAssociatedBorders(List<Segment> segments) {
        List<Border> bordersList = new ArrayList<>();
        for (Segment segment : segments) {
            bordersList.add(
                borders.get(segment.getId())
            );
        }
        return bordersList;
    }

    /**
     * Converts Segments into Borders
     * @param segments, SegmentSet -> EnhancedSet of Segments to prevent duplicates
     */
    private void convert(SegmentSet segments) {
        for (Segment segment : segments) {
            VertexDecorator v1 = vertexDecorators.get(segment.getV1().getId());
            VertexDecorator v2 = vertexDecorators.get(segment.getV2().getId());

            borders.add(
                Border.newBuilder().addSegment(segment).addV1(v1).addV2(v2).build()
            );
        }
    }

    /**
     * Converts Vertices into VertexDecorators
     * @param vertices, VertexSet -> EnhancedSet of Vertices to prevent duplicates
     */
    private void convert(VertexSet vertices) {
        for (Vertex vertex : vertices) {
            vertexDecorators.add(
                VertexDecorator.newBuilder().addVertex(vertex).build()
            );
        }
    }
}
