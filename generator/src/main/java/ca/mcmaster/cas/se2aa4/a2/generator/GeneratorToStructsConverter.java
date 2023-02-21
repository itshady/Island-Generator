package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class GeneratorToStructsConverter {
    public Set<Structs.Vertex> convertVertices(Map<Integer, Vertex> vertices) {
//        System.out.println("vertices: " + vertices.size());
        return extractVertices(mapToSet(vertices));
    }

    public Set<Structs.Segment> convertSegments(Map<Integer, Segment> segments) {
        return extractSegments(segments);
    }

    public Set<Structs.Polygon> convertPolygons(Map<Integer, Polygon> polygons) {
        return extractPolygons(polygons);
    }

    private Set<Vertex> mapToSet(Map<Integer, Vertex> vertices) {
        Set<Vertex> set = new LinkedHashSet<>();
        int counter = 0;
        int counter2 = 0;
        for (Vertex vertex : vertices.values()) {
            if (set.contains(vertex)) {
                counter++;

//                System.out.println("HEREEE" + vertex);
            }
            else {
                counter2++;
                set.add(vertex);
            }
        }
        System.out.println("counter: " + counter + "    counter2: "+counter2);
        return set;
    }

    private Set<Structs.Vertex> extractVertices(Set<Vertex> vertices) {
        Set<Structs.Vertex> vertexSet = new LinkedHashSet<>();
        int counter = 0;
        for (Vertex vertex : vertices) {
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
}
