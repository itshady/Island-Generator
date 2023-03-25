package ca.mcmaster.cas.se2aa4.a2.island.Island;

import Geometries.Coordinate;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {
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
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(new Segment(v1, v2));
        segmentList.add(new Segment(v2, v3));
        segmentList.add(new Segment(v3, v4));
        segmentList.add(new Segment(v1, v3));
        Polygon mockPolygon = new Polygon(segmentList);

        vertices.add(VertexDecorator.newBuilder().addVertex(v1).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v2).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v3).build());
        vertices.add(VertexDecorator.newBuilder().addVertex(v4).build());
        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);


        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(1)).addSegment(new Segment(v1,  v2)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(1)).addV2(vertices.get(2)).addSegment(new Segment(v2,  v3)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(2)).addV2(vertices.get(3)).addSegment(new Segment(v3,  v4)).build());

        Tile tile = Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid).build();

        tiles.add(tile);
    }

    @Test
    public void RegisterTest() {
        Island island = new Island();
        island.register(vertices, borders, tiles);
        assertEquals(vertices, island.getVertexDecorators());
        assertEquals(borders, island.getBorders());
        assertEquals(tiles, island.getTiles());
    }

    @Test
    public void DetermineCenterTest() {
        // center should be (5,5) since the smallest coord is (0,0) and largest (10,10)
        Island island = new Island();
        island.register(vertices, borders, tiles);

        assertEquals(new Coordinate(5,5), island.center());
    }

    @Test
    public void WidthandHeightTest() {
        // the smallest coord is (0,0) and largest (10,15)
        Island island = new Island();
        vertices.add(VertexDecorator.newBuilder().addVertex(new Vertex(10.0,15.0)).build());
        island.register(vertices, borders, tiles);

        assertEquals(10, island.width());
        assertEquals(15, island.height());
    }

    @Test
    public void ShallowCopyTest() {
        // the smallest coord is (0,0) and largest (10,15)
        Island island = new Island();
        island.register(vertices, borders, tiles);
        Integer numOfVertices = island.getVertexDecorators().size();

        // Action
        island.getVertexDecorators().add(VertexDecorator.newBuilder().addVertex(new Vertex(10.0,15.0)).build());

        // Assertion
        assertEquals(numOfVertices, island.getVertexDecorators().size());
    }
}