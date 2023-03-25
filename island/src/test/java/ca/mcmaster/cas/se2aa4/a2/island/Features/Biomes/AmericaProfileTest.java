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

public class AmericaProfileTest {

    Tile tile;
    AmericaProfile americaProfile = new AmericaProfile();

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

        // set two land tiles that have min and max

        island = new Island();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        island.register(new ArrayList<>(), new ArrayList<>(), tiles);
    }


    @Test
    public void DesertTest() {
        // Action
        tile.setAbsorption(0.0);
        tile.setAltitude(ElevationUtil.minAltitude + 50);

        americaProfile.process(island);

        // Assertion
        assertEquals(Biome.DESERT, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(0.0);
        tile.setAltitude(ElevationUtil.minAltitude + 51);

        americaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.DESERT, tile.getBiome());

    }

    @Test
    public void TundraTest() {
        // Action
        tile.setAbsorption(24.0);
        tile.setAltitude(ElevationUtil.minAltitude + 51);

        americaProfile.process(island);

        // Assertion
        assertEquals(Biome.TUNDRA, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(27.0);
        tile.setAltitude(ElevationUtil.minAltitude + 51);

        americaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.TUNDRA, tile.getBiome());
    }

    @Test
    public void MixedForestTest() {
        // Action
        tile.setAbsorption(64.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        americaProfile.process(island);

        // Assertion
        assertEquals(Biome.MIXEDFOREST, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(65.0);
        tile.setAltitude(ElevationUtil.minAltitude + 51);

        americaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.MIXEDFOREST, tile.getBiome());
    }

    @Test
    public void GrasslandTest() {
        // Action
        tile.setAbsorption(64.0);
        tile.setAltitude(ElevationUtil.minAltitude + 74);

        americaProfile.process(island);

        // Assertion
        assertEquals(Biome.GRASSLAND, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(64.0);
        tile.setAltitude(ElevationUtil.minAltitude + 76);

        americaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.GRASSLAND, tile.getBiome());
    }
    @Test
    public void MontaneForestTest() {
        // Action
        tile.setAbsorption(99.0);
        tile.setAltitude(ElevationUtil.minAltitude + 121);

        americaProfile.process(island);

        // Assertion
        assertEquals(Biome.MONTANEFOREST, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(64.0);
        tile.setAltitude(ElevationUtil.minAltitude + 90);

        americaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.MONTANEFOREST, tile.getBiome());
    }
    @Test
    public void SavannaTest() {
        // Action
        tile.setAbsorption(99.9);
        tile.setAltitude(ElevationUtil.minAltitude + 119);

        americaProfile.process(island);

        // Assertion
        assertEquals(Biome.SAVANNA, tile.getBiome());

        // Action

        // Out of boundary conditions
        tile.setAbsorption(99.9);
        tile.setAltitude(ElevationUtil.minAltitude + 121);

        americaProfile.process(island);

        // Assertion
        assertNotEquals(Biome.SAVANNA, tile.getBiome());
    }


}
