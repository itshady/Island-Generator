package ca.mcmaster.cas.se2aa4.a2.generator.Helpers;

import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.SegmentSet;
import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.VertexSet;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Centroid;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Vertex;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JTSToGeneratorConverter {
    // Takes in empty lists of vertices, segments, polygons, and centroids. Also takes a list of JTS polygon data (generated via voronoi diagram) and populates the empty geometry lists with the JTS data.
    public void convertAllData(List<Geometry> polygonsJTS, Map<Integer, Vertex> vertices, Map<Integer, Segment> segments, Map<Integer, ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Polygon> polygons, List<Centroid> centroids) {
        JTSDataConversion(polygonsJTS, vertices, segments, polygons, centroids);
    }

    private void JTSDataConversion(List<Geometry> polygonsJTS, Map<Integer, Vertex> vertices, Map<Integer, Segment> segments, Map<Integer, ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Polygon> polygons, List<Centroid> centroids) {
        int polyCounter = 0;

        VertexSet vertexSet = new VertexSet();
        SegmentSet segmentSet = new SegmentSet();
        for (Geometry polygon : polygonsJTS) {
            // adds vertices and segments of the polygon to the vertex and segment list
            Coordinate[] coords = polygon.getCoordinates();
            List<Segment> polySegments = new ArrayList<>();
            addVerticesAndSegments(vertices, segments, vertexSet, segmentSet, coords, polySegments);

            // get centroid
            polyCounter = addPolygon(vertices, polygons, centroids, polyCounter, vertexSet, polygon, polySegments);
        }
    }

    private int addPolygon(Map<Integer, Vertex> vertices, Map<Integer, Polygon> polygons, List<Centroid> centroids, int polyCounter, VertexSet vertexSet, Geometry polygon, List<Segment> polySegments) {
        Polygon newPolygon = new Polygon(polySegments, Color.BLACK, 2f);
        newPolygon.setId(polyCounter);
        addCentroid(vertices, centroids, vertexSet, polygon, newPolygon);
        polygons.put(polyCounter, newPolygon);
        polyCounter++;
        return polyCounter;
    }

    private void addCentroid(Map<Integer, Vertex> vertices, List<Centroid> centroids, VertexSet vertexSet, Geometry polygon, Polygon newPolygon) {
        org.locationtech.jts.algorithm.Centroid centroidJTS = new org.locationtech.jts.algorithm.Centroid(polygon);
        Centroid newCentroid = new Centroid(centroidJTS.getCentroid().getX(), centroidJTS.getCentroid().getY());
        newPolygon.setCentroid(newCentroid);
        centroids.add(newCentroid);
        Integer id = vertexSet.add(newCentroid);
        vertices.put(id, newCentroid);
    }

    private void addVerticesAndSegments(Map<Integer, Vertex> vertices, Map<Integer, Segment> segments, VertexSet vertexSet, SegmentSet segmentSet, Coordinate[] coords, List<Segment> polySegments) {
        for (int i = 0; i< coords.length-1; i++) {
            Vertex v1 = new Vertex(coords[i].getX(), coords[i].getY());
            Vertex v2 = new Vertex(coords[i+1].getX(), coords[i+1].getY());
            boolean containedV1 = vertexSet.contains(v1);
            boolean containedV2 = vertexSet.contains(v2);
            Integer id1 = vertexSet.add(v1);
            Integer id2 = vertexSet.add(v2);

            if (!containedV1) {
                v1.setId(id1);
                vertices.put(id1, v1);
            }

            if (!containedV2) {
                v2.setId(id2);
                vertices.put(id2, v2);
            }

            Segment newSeg = new Segment(vertexSet.get(id1), vertexSet.get(id2));
            Integer segId = segmentSet.add(newSeg);
            segments.put(segId, segmentSet.get(segId));
            polySegments.add(segmentSet.get(segId));
        }
    }
}
