package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Centroid;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExporterTest {
    @Test
    public void StructsToMeshTest() {
    // Left this as it is not to be tested for island.
    // This is a MeshADT functionality that we rewrote to show how proper OOP would be done here.
    }

    @Test
    public void MeshToIslandTest() {
        //Context
        VertexSet vertices = new VertexSet();
        Vertex v1 = new Vertex(0.0,0.0);
        Centroid v2 = new Centroid(0.0,10.0);
        Vertex v3 = new Vertex(10.0,10.0);
        Vertex v4 = new Vertex(10.0,0.0);
        vertices.add(v1);
        vertices.add(v2);
        vertices.add(v3);
        vertices.add(v4);
        SegmentSet segmentList = new SegmentSet();
        segmentList.add(new Segment(v1, v2));
        segmentList.add(new Segment(v2, v3));
        segmentList.add(new Segment(v3, v4));
        segmentList.add(new Segment(v1, v3));
        PolygonSet polygons = new PolygonSet();
        List<Segment> polygonsSides = new ArrayList<>();
        polygonsSides.add(segmentList.get(0));
        polygonsSides.add(segmentList.get(1));
        polygonsSides.add(segmentList.get(2));
        polygonsSides.add(segmentList.get(3));
        polygons.add(new Polygon(polygonsSides));
        polygons.get(0).setCentroid(v2);
        Mesh mesh = new Mesh();
        mesh.polygons = polygons;
        mesh.segments = segmentList;
        mesh.vertices = vertices;

        // Action
        Island island = new Exporter().upgrade(mesh);

        // Assertion
        assertEquals(polygons.get(0), island.getTile(0).getPolygon());
        assertEquals(4, island.getBorders().size());
        assertEquals(4, island.getVertexDecorators().size());
    }

    @Test
    public void IslandToMeshTest() {
        //Context
        Vertex v1 = new Vertex(0.0,0.0);
        Vertex v2 = new Vertex(0.0,10.0);
        Vertex v3 = new Vertex(10.0,10.0);
        Vertex v4 = new Vertex(10.0,0.0);
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(new Segment(v1, v2));
        segmentList.add(new Segment(v2, v3));
        segmentList.add(new Segment(v3, v4));
        segmentList.add(new Segment(v1, v3));
        Polygon mockPolygon = new Polygon(segmentList);

        List<VertexDecorator> vertices = new ArrayList<>();
        vertices.add(VertexDecorator.newBuilder().addVertex(v1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4).build());
        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

        List<Border> borders = new ArrayList<>();
        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(1)).addSegment(new Segment(v1,  v2)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(1)).addV2(vertices.get(2)).addSegment(new Segment(v2,  v3)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(2)).addV2(vertices.get(3)).addSegment(new Segment(v3,  v4)).build());

        Tile tile = Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid).build();
        Island island = new Island();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        island.register(vertices, borders, tiles);

        // Action
        Mesh mesh = new Exporter().process(island);

        // Assertions
        assertEquals(tile.getPolygon(), mesh.polygons.get(0));
        assertEquals(borders.size(), mesh.segments.toList().size());
        assertEquals(vertices.size(), mesh.vertices.toList().size());
    }

    @Test
    public void MeshToStructsTest() {
        // Left this as it is not to be tested for island.
        // This is a MeshADT functionality that we rewrote to show how proper OOP would be done here.
    }
}