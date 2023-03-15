package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Exporter {

    public Exporter() {
    }

    public Mesh upgrade(Structs.Mesh mesh) {
        StructsToMeshConverter converter = new StructsToMeshConverter(mesh);
        return converter.process();
    }

    public Island upgrade(Mesh mesh) {
        MeshToIslandConverter converter = new MeshToIslandConverter(mesh);
        return converter.process();
    }

    public Mesh process(Island island) {
        Mesh mesh = new Mesh();
        mesh.vertices = new VertexSet(convertToVertices(island.getVertexDecorators()));
        mesh.segments = new SegmentSet(convertToSegments(island.getSegments()));

        mesh.polygons = convertToPolygons(island.getTiles());
        return mesh;
    }

    private List<Vertex> convertToVertices(List<VertexDecorator> decorators) {
        List<Vertex> vertices = new ArrayList<>();
        for (VertexDecorator decorator : decorators) {
            vertices.add(decorator.getVertex());
        }
        return vertices;
    }

    private List<Segment> convertToSegments(List<Border> borders) {
        List<Segment> segments = new ArrayList<>();
        for (Border border : borders) {
            segments.add(border.getSegment());
        }
        return segments;
    }

    private PolygonSet convertToPolygons(List<Tile> tiles) {
        PolygonSet set = new PolygonSet();
        for (Tile tile : tiles) {
            tile.enhancePolygon();
            set.add(tile.getPolygon());
        }
        return set;
    }

    public Structs.Mesh process(Mesh mesh) {
        return Structs.Mesh.newBuilder()
                .addAllVertices(convertVertices(mesh.vertices))
                .addAllSegments(convertSegments(mesh.segments))
                .addAllPolygons(convertPolygons(mesh.polygons))
                .build();
    }

    /**
     * Iterates through the GeometrySet, associates an ID for every Vertex, and returns a Set
     * @param vertices is a GeometrySet of type Vertex (Geometries Package)
     * @return Set that contains the type Structs.Vertex
     */
    private Set<Structs.Vertex> convertVertices(GeometrySet<Vertex> vertices) {
        Set<Structs.Vertex> vertexSet = new LinkedHashSet<>();
        int counter = 0;
        for (Vertex vertex : vertices) {
            vertex.setId(counter);
            vertexSet.add(vertex.getVertex());
            counter++;
        }
        return vertexSet;
    }

    /**
     * Iterates through the GeometrySet, associates an ID for every Segment, and returns a Set
     * @param segments is a GeometrySet of type Segment (Geometries Package)
     * @return Set that contains the type Structs.Segment
     */
    private Set<Structs.Segment> convertSegments(GeometrySet<Segment> segments) {
        Set<Structs.Segment> segmentSet = new LinkedHashSet<>();
        int counter = 0;
        for (Segment segment : segments) {
            segment.setId(counter);
            segment.generateSegment();
            segmentSet.add(segment.getSegment());
            counter++;
        }
        return segmentSet;
    }

    /**
     * Iterates through the GeometrySet, associates an ID for every Polygon, and returns a Set
     * @param polygons is a GeometrySet of type Polygon (Geometries Package)
     * @return Set that contains the type Structs.Polygon
     */
    private Set<Structs.Polygon> convertPolygons(GeometrySet<Polygon> polygons) {
        Set<Structs.Polygon> polygonSet = new LinkedHashSet<>();
        for (Polygon polygon: polygons) {
            polygon.generatePolygon();
            polygonSet.add(polygon.getPolygon());
        }
        return polygonSet;
    }
}
