package ca.mcmaster.cas.se2aa4.a2.generator.Helpers;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.GeometrySet;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * GeneratorToStructsConverter converts our Generator data structures (Polygon, Segment, Vertex)
 * Into data structures that the IO Library supports
 *
 */
public class GeneratorToStructsConverter {

    /**
     * Calls the method extractVertices and returns a Set compatible with the IO library
     * @param vertices is a GeometrySet of type Vertex (Geometries Package)
     * @return Set that contains the type Structs.Vertex
     */
    public Set<Structs.Vertex> convertVertices(GeometrySet<Vertex> vertices) {
        return extractVertices(vertices);
    }

    /**
     * Calls the method extractSegments and returns a set compatible with the IO library
     * @param segments is a GeometrySet of type Segment (Geometries Package)
     * @return Set that contains the type Structs.Segment
     */
    public Set<Structs.Segment> convertSegments(GeometrySet<Segment> segments) {
        return extractSegments(segments);
    }

    /**
     * Calls the method extractPolygons and returns a set compatible with the IO library
     * @param polygons is a GeometrySet of type Polygon (Geometries Package)
     * @return Set that contains the type Structs.Polygon
     */
    public Set<Structs.Polygon> convertPolygons(GeometrySet<Polygon> polygons) {
        return extractPolygons(polygons);
    }

    /**
     * Iterates through the GeometrySet, associates an ID for every Vertex, and returns a Set
     * @param vertices is a GeometrySet of type Vertex (Geometries Package)
     * @return Set that contains the type Structs.Vertex
     */
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

    /**
     * Iterates through the GeometrySet, associates an ID for every Segment, and returns a Set
     * @param segments is a GeometrySet of type Segment (Geometries Package)
     * @return Set that contains the type Structs.Segment
     */
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

    /**
     * Iterates through the GeometrySet, associates an ID for every Polygon, and returns a Set
     * @param polygons is a GeometrySet of type Polygon (Geometries Package)
     * @return Set that contains the type Structs.Polygon
     */
    private Set<Structs.Polygon> extractPolygons(GeometrySet<Polygon> polygons) {
        Set<Structs.Polygon> polygonSet = new LinkedHashSet<>();
        for (Polygon polygon: polygons) {
            polygon.generatePolygon();
            polygonSet.add(polygon.getPolygon());
        }
        return polygonSet;
    }
}
