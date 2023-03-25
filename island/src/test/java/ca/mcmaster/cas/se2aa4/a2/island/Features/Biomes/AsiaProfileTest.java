package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;


import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AsiaProfileTest {

    Tile tile;
    AsiaProfile asiaProfile = new AsiaProfile();

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
        VertexDecorator decorator1 = VertexDecorator.newBuilder().addVertex(v1).build();
        VertexDecorator decorator2 = VertexDecorator.newBuilder().addVertex(v2).build();
        Border border1 = Border.newBuilder().addV1(decorator1).addV2(decorator2).addSegment(new Segment(v1,  v2)).build();
        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        List<Border> mockBorder = new ArrayList<>();
        mockBorder.add(border1);
        tile = Tile.newBuilder().addPolygon(mockPolygon).addBorders(mockBorder).addCentroid(mockCentroid).build();
        island = new Island();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        island.register(new ArrayList<>(), new ArrayList<>(), tiles);
    }


    @Test
    public void DesertTest() {
        // Action
        tile.setAbsorption(0.0);
        tile.setAltitude(ElevationUtil.minAltitude + 74);

        asiaProfile.process(island);

        // Assertion
        assertEquals(Biome.DESERT, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(0.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        asiaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.DESERT, tile.getBiome());

    }

    @Test
    public void MountainTest() {
        // Action
        tile.setAbsorption(24.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        asiaProfile.process(island);

        // Assertion
        assertEquals(Biome.MOUNTAIN, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(27.0);
        tile.setAltitude(ElevationUtil.minAltitude + 74);

        asiaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.MOUNTAIN, tile.getBiome());
    }

    @Test
    public void MangroveTest() {
        // Action
        tile.setAbsorption(55.0);
        tile.setAltitude(ElevationUtil.minAltitude + 74);

        asiaProfile.process(island);

        // Assertion
        assertEquals(Biome.MANGROVE, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(54.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        asiaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.MANGROVE, tile.getBiome());
    }

    @Test
    public void AlpineTundraTest() {
        // Action
        tile.setAbsorption(64.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        asiaProfile.process(island);

        // Assertion
        assertEquals(Biome.ALPINETUNDRA, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(44.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        asiaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.ALPINETUNDRA, tile.getBiome());
    }
    @Test
    public void RainForestTest() {
        // Action
        tile.setAbsorption(99.0);
        tile.setAltitude(ElevationUtil.minAltitude + 74);

        asiaProfile.process(island);

        // Assertion
        assertEquals(Biome.RAINFOREST, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(65.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        asiaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.RAINFOREST, tile.getBiome());
    }

}
