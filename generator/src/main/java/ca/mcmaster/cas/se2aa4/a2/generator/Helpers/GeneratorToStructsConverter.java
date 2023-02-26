package ca.mcmaster.cas.se2aa4.a2.generator.Helpers;

import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.GeometrySet;
import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.PolygonSet;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GeneratorToStructsConverter {
    public Set<Structs.Vertex> convertVertices(Map<Integer, Vertex> vertices) {
        return extractVertices(vertices);
    }

    public Set<Structs.Segment> convertSegments(Map<Integer, Segment> segments) {
        return extractSegments(segments);
    }

    public Set<Structs.Polygon> convertPolygons(Map<Integer, Polygon> polygons) {
        return extractPolygons(polygons);
    }

    public Set<Structs.Vertex> convertVertices(GeometrySet<Vertex> vertices) {
        return extractVertices(vertices);
    }

    public Set<Structs.Segment> convertSegments(GeometrySet<Segment> segments) {
        return extractSegments(segments);
    }

    public Set<Structs.Polygon> convertPolygons(GeometrySet<Polygon> polygons) {
        return extractPolygons(polygons);
    }


    private Set<Structs.Vertex> extractVertices(Map<Integer, Vertex> vertices) {
        Set<Structs.Vertex> vertexSet = new LinkedHashSet<>();
        int counter = 0;
        for (Vertex vertex : vertices.values()) {
            vertex.setId(counter);
            vertexSet.add(vertex.getVertex());
            counter++;
        }
        return vertexSet;
    }

    private Set<Structs.Segment> extractSegments(Map<Integer, Segment> segments) {
        Set<Structs.Segment> segmentSet = new LinkedHashSet<>();
        int counter = 0;
        for (Segment segment : segments.values()) {
            segment.setId(counter);
            segment.generateSegment();
            segmentSet.add(segment.getSegment());
            counter++;
        }
        return segmentSet;
    }

    private Set<Structs.Polygon> extractPolygons(Map<Integer, Polygon> polygons) {
        Set<Structs.Polygon> polygonSet = new LinkedHashSet<>();
        for (Polygon polygon: polygons.values()) {
            polygon.generatePolygon();
            polygonSet.add(polygon.getPolygon());
        }
        return polygonSet;
    }

    private Set<Structs.Vertex> extractVertices(GeometrySet<Vertex> vertices) {
        Set<Structs.Vertex> vertexSet = new LinkedHashSet<>();
        int counter = 0;
        for (Vertex vertex : vertices) {
            vertex.setId(counter);
            vertexSet.add(vertex.getVertex());
            counter++;
        }
        return vertexSet;
    }

    private Set<Structs.Segment> extractSegments(GeometrySet<Segment> segments) {
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

    private Set<Structs.Polygon> extractPolygons(GeometrySet<Polygon> polygons) {
        Set<Structs.Polygon> polygonSet = new LinkedHashSet<>();
        for (Polygon polygon: polygons) {
            polygon.generatePolygon();
            polygonSet.add(polygon.getPolygon());
        }
        return polygonSet;
    }
}
