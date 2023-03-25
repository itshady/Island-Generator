package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SoilUtilTest {

    Tile tile1;
    Tile tile2;
    Island island;

    @BeforeEach
    public void setup() {
        //Context
        List<Tile> tiles = new ArrayList<>();
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
        List<Border> mockBorder = new ArrayList<>();
        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(0)).addSegment(new Segment(v1,  v2)).build());
        mockBorder.add(borders.get(0));

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        VertexDecorator mockCentroid2 = VertexDecorator.newBuilder().addVertex(new Vertex(6.0,5.0)).build();

        vertices.add(mockCentroid);
        vertices.add(mockCentroid2);

        tile1 = Tile.newBuilder().addPolygon(mockPolygon).addBorders(mockBorder).addCentroid(mockCentroid).build();
        tile2 = Tile.newBuilder().addPolygon(mockPolygon).addBorders(mockBorder).addCentroid(mockCentroid2).build();

        tiles.add(tile1);
        tiles.add(tile2);

        island = new Island();
        island.register(vertices, new ArrayList<>(), tiles);
    }
    @Test
    public void LakeMoistureTest() {
        tile1.setWater(new Lake());
        new DryProfile().process(island);

        assertEquals(new Lake().moisture()*1.0, tile1.getAbsorption());
    }

    @Test
    public void OceanMoistureTest() {
        tile1.setWater(new Ocean());
        new DryProfile().process(island);

        assertEquals(new Ocean().moisture()*1.0, tile1.getAbsorption());
    }
}