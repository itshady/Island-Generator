package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DryProfileTest {

    static Tile tile1;
    static Tile tile2;
    static Island island;

    @BeforeEach
    public void setup() {
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

        List<Border> borders = new ArrayList<>();
        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(0)).addSegment(new Segment(v1,  v2)).build());
        List<Border> mockBorder = new ArrayList<>();
        mockBorder.add(borders.get(0));
        // Added to ensure island is 500 x 500
        Vertex farVertex = new Vertex(500.0,500.0);
        vertices.add(VertexDecorator.newBuilder().addVertex(farVertex).build());

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

        tile1 = Tile.newBuilder().addPolygon(mockPolygon).addBorders(mockBorder).addCentroid(mockCentroid).build();
        island = new Island();
        List<Tile> tiles = new ArrayList<>();
        VertexDecorator mockCentroid2 = VertexDecorator.newBuilder().addVertex(new Vertex(6.0,5.0)).build();
        vertices.add(mockCentroid2);
        tile2 = Tile.newBuilder().addPolygon(mockPolygon).addBorders(mockBorder).addCentroid(mockCentroid2).build();
        tiles.add(tile1);
        tiles.add(tile2);
        island.register(vertices, new ArrayList<>(), tiles);
    }
    @Test
    public void moistureTest() {
        tile1.setWater(new Lake());
        new DryProfile().process(island);
        System.out.println(tile1.hasLake());
        System.out.println(tile1.getWater().moisture());
        System.out.println(tile2.isLand());
        System.out.println(tile2.getAltitude());
        System.out.println(tile2.isOcean());
        System.out.println(tile2.getSoilProfile());
        assertEquals(5, tile2.getAbsorption());
    }
}