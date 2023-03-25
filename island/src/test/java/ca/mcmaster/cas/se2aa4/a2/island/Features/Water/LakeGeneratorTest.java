package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.AmericaProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LakeGeneratorTest {
    List<Segment> segmentList = new ArrayList<>();
    Polygon mockPolygon;
    static Tile tile;
    static Tile tile2;
    static Tile tile3;
    static Island island;
    List<VertexDecorator> vertices = new ArrayList<>();
    List<Border> borders = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();

    @BeforeEach
    public void setup() {
        //Context
        Vertex v1 = new Vertex(0.0,0.0);
        Vertex v2 = new Vertex(0.0,10.0);
        Vertex v3 = new Vertex(10.0,10.0);
        Vertex v4 = new Vertex(10.0,0.0);

        segmentList.add(new Segment(v1, v2));
        segmentList.add(new Segment(v2, v3));
        segmentList.add(new Segment(v3, v4));
        segmentList.add(new Segment(v1, v3));

        mockPolygon = new Polygon(segmentList);
        vertices.add(VertexDecorator.newBuilder().addVertex(v1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4).build());

        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(0)).addSegment(new Segment(v1,  v2)).build());

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(25.0,25.0)).build();
        vertices.add(mockCentroid);

        tile = Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid).build();

        island = new Island();
        tiles.add(tile);
        island.register(vertices, new ArrayList<>(), tiles);
    }

    @Test
    public void GenerateLakeTest() {
        new LakeGenerator().process(island, 1);
        assertTrue(tile.hasLake());
    }

    @Test
    public void GenerateSpecificNumberOfLakesTest() {
        VertexDecorator mockCentroid2 = VertexDecorator.newBuilder().addVertex(new Vertex(50.0,50.0)).build();
        vertices.add(mockCentroid2);
        segmentList.add(new Segment(new Vertex(12.0,32.0),new Vertex(12.0,20.0)));
        Polygon polygon2 = new Polygon(segmentList);
        tile2 = Tile.newBuilder().addPolygon(polygon2).addBorders(borders).addCentroid(mockCentroid2).build();
        island.getTiles().add(tile2);

        VertexDecorator mockCentroid3 = VertexDecorator.newBuilder().addVertex(new Vertex(100.0,100.0)).build();
        vertices.add(mockCentroid3);

        segmentList.add(new Segment(new Vertex(131.0,22.0),new Vertex(11.0,23.0)));
        Polygon polygon3 = new Polygon(segmentList);
        tile3 = Tile.newBuilder().addPolygon(polygon3).addBorders(borders).addCentroid(mockCentroid3).build();
        island.getTiles().add(tile3);

        new LakeGenerator().process(island, 2);

        int lakeCounter = 0;
        for (Tile tile : island.getTiles()) {
            if (tile.hasLake()) lakeCounter++;
        }
        assertEquals(2, lakeCounter);
    }

    @Test
    public void GenerateNoLakeTest() {
        new LakeGenerator().process(island, 0);
        assertFalse(tile.hasLake());
    }
}