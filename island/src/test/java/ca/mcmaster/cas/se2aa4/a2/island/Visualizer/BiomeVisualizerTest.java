package ca.mcmaster.cas.se2aa4.a2.island.Visualizer;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.Biome;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.SoilUtil;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BiomeVisualizerTest {
    static Tile tile;
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

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

        tile = Tile.newBuilder().addPolygon(mockPolygon).addBorders(mockBorder).addCentroid(mockCentroid).build();
        island = new Island();
        List<Tile> tiles = new ArrayList<>();
        tiles.add(tile);
        island.register(vertices, new ArrayList<>(), tiles);
    }

    @Test
    public void MountainTest() {
        tile.setBiome(Biome.MOUNTAIN);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.MOUNTAIN.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void MontaneForestTest() {
        tile.setBiome(Biome.MONTANEFOREST);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.MONTANEFOREST.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void DesertTest() {
        tile.setBiome(Biome.DESERT);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.DESERT.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void GrasslandTest() {
        tile.setBiome(Biome.GRASSLAND);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.GRASSLAND.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void SavannaTest() {
        tile.setBiome(Biome.SAVANNA);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.SAVANNA.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void AlpineTundraTest() {
        tile.setBiome(Biome.ALPINETUNDRA);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.ALPINETUNDRA.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void MangroveTest() {
        tile.setBiome(Biome.MANGROVE);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.MANGROVE.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void TundraTest() {
        tile.setBiome(Biome.TUNDRA);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.TUNDRA.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void MixedForestTest() {
        tile.setBiome(Biome.MIXEDFOREST);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.MIXEDFOREST.toColor(), tile.getPolygon().getColor());
    }

    @Test
    public void RainForestTest() {
        tile.setBiome(Biome.RAINFOREST);
        new BiomeVisualizer().process(island);

        assertEquals(Biome.RAINFOREST.toColor(), tile.getPolygon().getColor());
    }
}