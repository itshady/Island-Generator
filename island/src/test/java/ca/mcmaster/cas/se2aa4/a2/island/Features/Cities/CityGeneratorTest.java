package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks.StarNetwork;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CityGeneratorTest {
    List<Segment> segmentList = new ArrayList<>();
    List<Polygon> polygons = new ArrayList<>();
    Island island = new Island();
    List<VertexDecorator> vertices = new ArrayList<>();
    List<Border> borders = new ArrayList<>();
    List<Tile> tiles = new ArrayList<>();

    @BeforeEach
    public void setup() {
        //Context for Polygon 1 (near edge of map)
        Vertex v1 = new Vertex(0.0,0.0);
        Vertex v2 = new Vertex(0.0,10.0);
        Vertex v3 = new Vertex(10.0,10.0);
        Vertex v4 = new Vertex(10.0,0.0);

        segmentList.add(new Segment(v1, v2));
        segmentList.add(new Segment(v2, v3));
        segmentList.add(new Segment(v3, v4));
        segmentList.add(new Segment(v4, v1));

        polygons.add(new Polygon(segmentList));
        vertices.add(VertexDecorator.newBuilder().addVertex(v1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4).build());

        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(1)).addSegment(segmentList.get(0)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(1)).addV2(vertices.get(2)).addSegment(segmentList.get(1)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(2)).addV2(vertices.get(3)).addSegment(segmentList.get(2)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(3)).addV2(vertices.get(0)).addSegment(segmentList.get(3)).build());

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

        tiles.add(Tile.newBuilder().addPolygon(polygons.get(0)).addBorders(borders).addCentroid(mockCentroid).build());

        //Context for Polygon 1 (near edge of map)
        Vertex v1_1 = new Vertex(240.0,240.0);
        Vertex v2_1 = new Vertex(240.0,250.0);
        Vertex v3_1 = new Vertex(250.0,250.0);
        Vertex v4_1 = new Vertex(250.0,240.0);

        segmentList.add(new Segment(v1_1, v2_1));
        segmentList.add(new Segment(v2_1, v3_1));
        segmentList.add(new Segment(v3_1, v4_1));
        segmentList.add(new Segment(v4_1, v1_1));

        polygons.add(new Polygon(segmentList));
        vertices.add(VertexDecorator.newBuilder().addVertex(v1_1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2_1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3_1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4_1).build());

        borders.add(Border.newBuilder().addV1(vertices.get(4)).addV2(vertices.get(5)).addSegment(segmentList.get(4)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(5)).addV2(vertices.get(6)).addSegment(segmentList.get(5)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(6)).addV2(vertices.get(7)).addSegment(segmentList.get(6)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(7)).addV2(vertices.get(4)).addSegment(segmentList.get(7)).build());

        VertexDecorator mockCentroid_1 = VertexDecorator.newBuilder().addVertex(new Vertex(245.0,245.0)).build();
        vertices.add(mockCentroid_1);
        tiles.add(Tile.newBuilder().addPolygon(polygons.get(1)).addBorders(borders).addCentroid(mockCentroid_1).build());

        // set neighbours
        polygons.get(0).setId(0);
        polygons.get(1).setId(1);
        polygons.get(0).addPolygonNeighbourSet(new HashSet<>(Collections.singletonList(polygons.get(1))));
        polygons.get(1).addPolygonNeighbourSet(new HashSet<>(Collections.singletonList(polygons.get(0))));


        // add vertex to make map 500x500
        vertices.add(VertexDecorator.newBuilder().addVertex(new Vertex(500.0, 500.0)).build());

        // Make island
        island.register(vertices, borders, tiles);
    }

    @Test
    public void GenerateOneCityTest() {
        new CityGenerator().process(island, 1, new StarNetwork());

        int counter = 0;
        for (Tile tile : tiles) {
            if (tile.getCentroid().isCity()) counter++;
        }
        assertEquals(1, counter);
    }

    @Test
    public void GenerateMultipleCitiesTest() {
        new CityGenerator().process(island, 2, new StarNetwork());

        int counter = 0;
        for (Tile tile : tiles) {
            if (tile.getCentroid().isCity()) counter++;
        }
        assertEquals(2, counter);
    }

    @Test
    public void RoadGenerationTest() {
        new CityGenerator().process(island, 2, new StarNetwork());

        List<Border> borders = island.getBorders().stream().filter(e ->
                e.getV1() == tiles.get(0).getCentroid() && e.getV2() == tiles.get(1).getCentroid() ||
                        e.getV2() == tiles.get(0).getCentroid() && e.getV1() == tiles.get(1).getCentroid()
        ).toList();

        assertEquals(1, borders.size());

        assertTrue(borders.get(0).hasRoad());

        List<Border> allOtherBorders = new ArrayList<>(island.getBorders());
        allOtherBorders.removeAll(borders);
        for (Border border : allOtherBorders) {
            assertFalse(border.hasRoad());
        }
    }
}