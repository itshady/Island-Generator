package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RiverGeneratorTest {
    List<Segment> segmentList = new ArrayList<>();
    Polygon mockPolygon;
    static Tile tile;
    static Island island;
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
        VertexDecorator d1 = VertexDecorator.newBuilder().addVertex(v1).build();
        VertexDecorator d2 = VertexDecorator.newBuilder().addVertex(v2).build();
        VertexDecorator d3 = VertexDecorator.newBuilder().addVertex(v3).build();
        VertexDecorator d4 = VertexDecorator.newBuilder().addVertex(v4).build();
        vertices.add(d1);
        vertices.add(d2);
        vertices.add(d3);
        vertices.add(d4);
        d1.setAltitude(20.0); d2.setAltitude(15.0); d3.setAltitude(10.0); d4.setAltitude(5.0);

        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(1)).addSegment(new Segment(v1,  v2)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(1)).addV2(vertices.get(2)).addSegment(new Segment(v2,  v3)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(2)).addV2(vertices.get(3)).addSegment(new Segment(v3,  v4)).build());
        borders.add(Border.newBuilder().addV1(vertices.get(0)).addV2(vertices.get(3)).addSegment(new Segment(v1,  v3)).build());

        VertexDecorator mockCentroid = VertexDecorator.newBuilder().addVertex(new Vertex(5.0,5.0)).build();
        vertices.add(mockCentroid);

        tile = Tile.newBuilder().addPolygon(mockPolygon).addBorders(borders).addCentroid(mockCentroid).build();

        island = new Island();
        tiles.add(tile);
        island.register(vertices, borders, tiles);
    }

    @Test
    public void GenerateRiverTest() {
        new RiverGenerator().process(island, 1);
        int count = 0;
        Set<VertexDecorator> springs = new HashSet<>();
        for (Border b: tile.getBorders()) {
            if (b.getV1().isSpring() && !springs.contains(b.getV1())) {
                count++;
                springs.add(b.getV1());
            } else if (b.getV2().isSpring() && !springs.contains(b.getV2())) {
                count++;
                springs.add(b.getV1());
            }
            assertEquals(1, count);
        }
    }

    @Test
    public void RiverFlowTest() {
        new RiverGenerator().process(island, 1);

        // Temporary initialization of spring
        VertexDecorator spring = tile.getBorders().get(0).getV1();

        // Find the VertexDecorator with the spring
        for (Border b : tile.getBorders()) {
            if (b.getV1().isSpring()) {
                spring = b.getV1();
                break;
            } else if (b.getV2().isSpring()) {
                spring = b.getV1();
                break;
            }
        }

        Set<Border> checkedBorders = new HashSet<>();
        boolean riverFlow = false;
        for (Border b1 : tile.getBorders()) {
            for (Border b : tile.getBorders()) {
                // Find the current vertex focused on and see if the next connected vertex has a lower altitude than the focused one.
                // CheckedBorders ensures that we have not already visited that border
                if (!checkedBorders.contains(b) && b.hasRiver() && (b.getV1() == spring || b.getV2() == spring)) {
                    if (b.getV1() == spring && (b.getV2().getAltitude() < b.getV1().getAltitude())) {
                        System.out.println("Spring Altitude: " + b.getV1().getAltitude() + " V2 Altitude: " + b.getV2().getAltitude());
                        spring = b.getV2();
                        riverFlow = true;
                        checkedBorders.add(b);
                        break;
                    } else if (!checkedBorders.contains(b) && b.getV2() == spring && (b.getV1().getAltitude() < b.getV2().getAltitude())) {
                        spring = b.getV1();
                        riverFlow = true;
                        checkedBorders.add(b);
                        break;
                    } else {
                        riverFlow = false;
                        fail();
                        break;
                    }
                }
            }
        }
            assertTrue(riverFlow);
    }


    @Test
    public void GenerateNoRiverTest() {
        new RiverGenerator().process(island, 0);
        int count = 0;
        Set<VertexDecorator> springs = new HashSet<>();
        for (Border b: tile.getBorders()) {
            if (b.getV1().isSpring() && !springs.contains(b.getV1())) {
                count++;
                springs.add(b.getV1());
            } else if (b.getV2().isSpring() && !springs.contains(b.getV2())) {
                count++;
                springs.add(b.getV1());
            }
            assertEquals(0, count);
        }
    }

}