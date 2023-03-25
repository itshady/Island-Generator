package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BuildersTest {

    Vertex v1;
    Vertex v2;
    Vertex v3;
    Vertex v4;
    Segment segment1;
    List<VertexDecorator> vertices = new ArrayList<>();
    List<Segment> segmentList = new ArrayList<>();
    List<Border> borders = new ArrayList<>();
    Polygon mockPolygon;
    VertexDecorator mockCentroid;

    @BeforeEach
    public void setup() {
        v1 = new Vertex(0.0,0.0);
        v2 = new Vertex(10.0, 0.0);
        v3 = new Vertex(10.0,10.0);
        v4 = new Vertex(10.0,0.0);

        segment1 = new Segment(v1, v2);
        segmentList.add(segment1);
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

        mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

    }

    @Test
    public void VertexDecoratorBuilderTest() {
        VertexDecorator vertexDecorator = VertexDecorator.newBuilder().addVertex(v1).build();

        // Check to see if original vertex equals its Island (VertexDecorator) equivalent
        assertEquals(v1, vertexDecorator.getVertex());

    }

    @Test
    public void BorderBuilderTest() {
        VertexDecorator v1D = vertices.get(0);
        VertexDecorator v2D = vertices.get(1);

        Border border = Border.newBuilder().addSegment(segment1).addV1(v1D).addV2(v2D).build();

        // Check to see if original segment equals its Island (Border) equivalent
        assertEquals(segment1, border.getSegment());

    }

    @Test
    public void BorderBuilderCheckVerticesTest() {
        VertexDecorator v1D = vertices.get(0);
        VertexDecorator v2D = vertices.get(1);

        Border border = Border.newBuilder().addSegment(segment1).addV1(v1D).addV2(v2D).build();

        assertEquals(v1D, border.getV1());
        assertEquals(v2D, border.getV2());

    }

    @Test
    public void TileBuilderTest() {

        Tile tile = Tile.newBuilder().addBorders(borders).addPolygon(mockPolygon).addCentroid(mockCentroid).build();

        // Check to see if original polygon equals its Island (Tile) equivalent
        assertEquals(mockPolygon, tile.getPolygon());

    }

    @Test
    public void TileCheckCentroidTest() {
        Tile tile = Tile.newBuilder().addBorders(borders).addPolygon(mockPolygon).addCentroid(mockCentroid).build();

        // Check to see if the centroid point is the same between original polygon and Island (Tile) equivalent
        assertEquals(mockCentroid, tile.getCentroid());
    }

    @Test
    public void TileCheckBordersTest() {
        Tile tile = Tile.newBuilder().addBorders(borders).addPolygon(mockPolygon).addCentroid(mockCentroid).build();

        // Check to see if the borders are the same between original polygon and Island (Tile) equivalent
        assertEquals(borders, tile.getBorders());
    }

}
