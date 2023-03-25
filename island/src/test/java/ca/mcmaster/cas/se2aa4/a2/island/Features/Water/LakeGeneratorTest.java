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

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LakeGeneratorTest {
    List<Segment> segmentList = new ArrayList<>();
    Polygon mockPolygon;
    Tile tile;
    Tile tile2;
    Tile tile3;
    Tile tile4;
    Island island;
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

        List<Tile> newTiles = island.getTiles();
        newTiles.add(tile2);

        VertexDecorator mockCentroid3 = VertexDecorator.newBuilder().addVertex(new Vertex(100.0,100.0)).build();
        vertices.add(mockCentroid3);

        segmentList.add(new Segment(new Vertex(131.0,22.0),new Vertex(11.0,23.0)));
        Polygon polygon3 = new Polygon(segmentList);
        tile3 = Tile.newBuilder().addPolygon(polygon3).addBorders(borders).addCentroid(mockCentroid3).build();
        newTiles.add(tile3);
        island.register(island.getVertexDecorators(), island.getBorders(), newTiles);

        new LakeGenerator().process(island, 2);

        int lakeCounter = 0;
        for (Tile tile : island.getTiles()) {
            if (tile.hasLake()) lakeCounter++;
        }
        assertEquals(2, lakeCounter);
    }

    @Test
    public void FlatLakes() {
        List<Tile> tiles = new ArrayList<>();
        // Making neighbour polygons to test for flat lakes
        Polygon polygon2 = new Polygon(segmentList);
        Polygon polygon3 = new Polygon(segmentList);
        Polygon polygon4 = new Polygon(segmentList);

        VertexDecorator mockCentroid2 = VertexDecorator.newBuilder().addVertex(new Vertex(26.0,26.0)).build();
        VertexDecorator mockCentroid3 = VertexDecorator.newBuilder().addVertex(new Vertex(27.0,27.0)).build();
        VertexDecorator mockCentroid4 = VertexDecorator.newBuilder().addVertex(new Vertex(28.0,28.0)).build();

        tile2 = Tile.newBuilder().addPolygon(polygon2).addBorders(borders).addCentroid(mockCentroid2).build();
        tile3 = Tile.newBuilder().addPolygon(polygon3).addBorders(borders).addCentroid(mockCentroid3).build();
        tile4 = Tile.newBuilder().addPolygon(polygon4).addBorders(borders).addCentroid(mockCentroid4).build();

        tile.getPolygon().setId(1);
        tile2.getPolygon().setId(2);
        tile3.getPolygon().setId(3);
        tile4.getPolygon().setId(4);

        tile.getCentroid().setAltitude(10.0);
        tile2.getCentroid().setAltitude(20.0);
        tile3.getCentroid().setAltitude(15.0);
        tile4.getCentroid().setAltitude(5.0);

        Set<Polygon> tileNeighbour = new HashSet<>();
        tileNeighbour.add(tile2.getPolygon());
        tileNeighbour.add(tile3.getPolygon());
        tileNeighbour.add(tile4.getPolygon());

        Set<Polygon> tileNeighbour2 = new HashSet<>();
        tileNeighbour2.add(tile.getPolygon());
        tileNeighbour2.add(tile3.getPolygon());
        tileNeighbour2.add(tile4.getPolygon());

        Set<Polygon> tileNeighbour3 = new HashSet<>();
        tileNeighbour3.add(tile.getPolygon());
        tileNeighbour3.add(tile2.getPolygon());
        tileNeighbour3.add(tile4.getPolygon());

        Set<Polygon> tileNeighbour4 = new HashSet<>();
        tileNeighbour4.add(tile.getPolygon());
        tileNeighbour4.add(tile2.getPolygon());
        tileNeighbour4.add(tile3.getPolygon());

        tile.getPolygon().addPolygonNeighbourSet(tileNeighbour);
        tile2.getPolygon().addPolygonNeighbourSet(tileNeighbour2);
        tile3.getPolygon().addPolygonNeighbourSet(tileNeighbour3);
        tile4.getPolygon().addPolygonNeighbourSet(tileNeighbour4);

        tiles.add(tile);
        tiles.add(tile2);
        tiles.add(tile4);
        tiles.add(tile4);

        island.register(island.getVertexDecorators(), island.getBorders(), tiles);

        new LakeGenerator().process(island, 1);
        // Action
        List<Tile> checkedTiles = new ArrayList<>();
        for (Tile tile : tiles) {
            if (checkedTiles.isEmpty() && tile.hasLake()) checkedTiles.add(tile);
            else if (!checkedTiles.isEmpty() && tile.hasLake() && !checkedTiles.contains(tile)) {
                assertEquals(tile.getCentroid().getAltitude(), checkedTiles.get(0).getCentroid().getAltitude());
            }
        }

    }

    @Test
    public void GenerateNoLakeTest() {
        new LakeGenerator().process(island, 0);
        assertFalse(tile.hasLake());
    }
}