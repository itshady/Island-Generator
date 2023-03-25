package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CraterProfileTest {
    Tile tile;
    Island island;

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

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

        tile = Tile.newBuilder().addPolygon(mockPolygon).addBorders(mockBorder).addCentroid(mockCentroid).build();
        island = new Island();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        island.register(vertices, new ArrayList<>(), tiles);
    }


    @Test
    public void CenterLowAltitudeTest() {
        // setup has 10x10 grid and centroid of our tile is (5,5) aka dead middle
        ElevationProfile elevation = new CraterProfile();
        elevation.process(island);

        assertEquals(ElevationUtil.minAltitude + 1, tile.getAltitude());
    }

    @Test
    public void OuterHighAltitudeTest() {
        // setup has 10x10 grid and centroid of our tile is (5,5) aka dead middle
        // add some vertex far right and it should be a tile far from the center of the crater.
        // Should reach MAX altitude if set extremely far
        ElevationProfile elevation = new CraterProfile();
        List<VertexDecorator> vertices = island.getVertexDecorators();
        Vertex farVertex = new Vertex(500.0,500.0);
        vertices.add(VertexDecorator.newBuilder().addVertex(farVertex).build());
        island.register(vertices, island.getBorders(), island.getTiles());
        elevation.process(island);

        assertEquals(ElevationUtil.maxAltitude, tile.getAltitude());
    }
}