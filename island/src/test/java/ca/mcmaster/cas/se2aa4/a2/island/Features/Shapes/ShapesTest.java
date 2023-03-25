package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ShapesTest {
    List<Segment> segmentList = new ArrayList<>();
    Polygon mockPolygon;
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

        mockPolygon = new Polygon(segmentList);
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

        tiles.add(Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid).build());

        //Context for Polygon 1 (near edge of map)
        Vertex v1_1 = new Vertex(240.0,240.0);
        Vertex v2_1 = new Vertex(240.0,250.0);
        Vertex v3_1 = new Vertex(250.0,250.0);
        Vertex v4_1 = new Vertex(250.0,240.0);

        segmentList.add(new Segment(v1_1, v2_1));
        segmentList.add(new Segment(v2_1, v3_1));
        segmentList.add(new Segment(v3_1, v4_1));
        segmentList.add(new Segment(v4_1, v1_1));

        mockPolygon = new Polygon(segmentList);
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
        tiles.add(Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid_1).build());

        // add vertex to make map 500x500
        vertices.add(VertexDecorator.newBuilder().addVertex(new Vertex(500.0, 500.0)).build());

        // Make island
        island.register(vertices, borders, tiles);
    }

    @Test
    public void CircleTest() {
        // fixture where there is a polygon closer to the center of the map and one closer to the edge
        new Circle().process(island);

        assertTrue(tiles.get(0).isOcean());
        assertTrue(tiles.get(1).isLand());
        assertFalse(tiles.get(1).hasBodyOfWater());
    }

    @Test
    public void SquareTest() {
        // fixture where there is a polygon closer to the center of the map and one closer to the edge
        new Square().process(island);

        assertTrue(tiles.get(0).isOcean());
        assertTrue(tiles.get(1).isLand());
        assertFalse(tiles.get(1).hasBodyOfWater());
    }

    @Test
    public void OvalTest() {
        // fixture where there is a polygon closer to the center of the map and one closer to the edge
        new Oval().process(island);

        assertTrue(tiles.get(0).isOcean());
        assertTrue(tiles.get(1).isLand());
        assertFalse(tiles.get(1).hasBodyOfWater());
    }

    @Test
    public void ThreeCircleTest() {
        // fixture where there is a polygon closer to the center of the map and one closer to the edge
        new ThreeCircle().process(island);

        assertTrue(tiles.get(0).isOcean());
        assertTrue(tiles.get(1).isLand());
        assertFalse(tiles.get(1).hasBodyOfWater());
    }

    @Test
    public void TriangleTest() {
        // fixture where there is a polygon closer to the center of the map and one closer to the edge
        new Triangle().process(island);

        assertTrue(tiles.get(0).isOcean());
        assertTrue(tiles.get(1).isLand());
        assertFalse(tiles.get(1).hasBodyOfWater());
    }
}