package Helpers;

import Geometries.Centroid;
import Geometries.Segment;
import Geometries.Vertex;
import Geometries.Polygon;
import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JTSToGeneratorConverter {
    private VertexSet vertexSet;
    private SegmentSet segmentSet;
    private PolygonSet polygonSet;
    private List<Centroid> centroids;

    public GeometrySet<Vertex> getVertices() {
        return vertexSet;
    }

    public GeometrySet<Segment> getSegments() {
        return segmentSet;
    }

    public GeometrySet<Polygon> getPolygons() {
        return polygonSet;
    }

    public List<Centroid> getCentroids() {
        return centroids;
    }

    /**
     * Takes in a list of JTS polygons, and calls the JTSDataConversion method
     * @param polygonsJTS is a List of type Geometry (generated via VoronoiDiagram)
     */
    public void convertAllData(List<Geometry> polygonsJTS) {
        JTSDataConversion(polygonsJTS);
    }

    /**
     * Takes a list of JTS polygon data and populates the empty Geometry List with JTS data
     * @param polygonsJTS is a List of type Geometry (generated via VoronoiDiagram)
     */
    private void JTSDataConversion(List<Geometry> polygonsJTS) {
        vertexSet = new VertexSet();
        polygonSet = new PolygonSet();
        segmentSet = new SegmentSet();
        centroids = new ArrayList<>();
        for (Geometry polygon : polygonsJTS) {
            // adds vertices and segments of the polygon to the vertex and segment list
            Coordinate[] coords = polygon.getCoordinates();
            List<Segment> polySegments = new ArrayList<>();
            addVerticesAndSegments(coords, polySegments);

            // get centroid
            addPolygon(polygon, polySegments);
        }
    }

    /**
     * Converts the JTS Polygon to a Polygon (Geometries Package)
     * @param polygon is of Type Geometry (generated from VoronoiDiagram)
     * @param polySegments is a Collection of segments that belong to that Polygon
     */
    private void addPolygon(Geometry polygon, List<Segment> polySegments) {
        Polygon newPolygon = new Polygon(polySegments, 2f);
        Integer polyId = polygonSet.add(newPolygon);
        newPolygon.setId(polyId);
        addCentroid(polygon, newPolygon);
    }

    /**
     * Calculates the centroid of the polygon
     * @param polygon is of Type Geometry (generated from VoronoiDiagram)
     * @param generatorPolygon is of Type Polygon (Geometries Package version)
     */
    private void addCentroid(Geometry polygon, Polygon generatorPolygon) {
        org.locationtech.jts.algorithm.Centroid centroidJTS = new org.locationtech.jts.algorithm.Centroid(polygon);
        Centroid newCentroid = new Centroid(centroidJTS.getCentroid().getX(), centroidJTS.getCentroid().getY());
        generatorPolygon.setCentroid(newCentroid);
        centroids.add(newCentroid);
        Integer id = vertexSet.add(newCentroid);
    }

    /**
     * Takes a Coordinate Array and a list of Segments and converts the JTS types to Geometry Package Types (Vertex, Segment)
     * @param coords is a Coordinate Array (generated by VoronoiDiagram)
     * @param polySegments is a Collection of segments that belong to that Polygon
     */
    private void addVerticesAndSegments(Coordinate[] coords, List<Segment> polySegments) {
        Random bag = new Random();
        for (int i = 0; i< coords.length-1; i++) {
            // For TA Testing purposes
            // Uncomment lines 99-102 and comment lines 104-105, for 2 colours randomly assigned to vertices
            boolean bool = bag.nextBoolean();
            Vertex v1 = new Vertex(coords[i].getX(), coords[i].getY(), bool ? new Color(30, 107, 252) : new Color(255, 58, 58));
            bool = bag.nextBoolean();
            Vertex v2 = new Vertex(coords[i+1].getX(), coords[i+1].getY(), bool ? new Color(30, 107, 252) : new Color(255, 58, 58));
            // Uncomment lines 104-105 and comment lines 99-102, for randomly generated rgba values
            // Vertex v1 = new Vertex(coords[i].getX(), coords[i].getY());
            // Vertex v2 = new Vertex(coords[i+1].getX(), coords[i+1].getY());
            Integer id1 = vertexSet.add(v1);
            Integer id2 = vertexSet.add(v2);
            v1.setId(id1);
            v2.setId(id2);

            Segment newSeg = new Segment(vertexSet.get(id1), vertexSet.get(id2));
            Integer segId = segmentSet.add(newSeg);
            polySegments.add(segmentSet.get(segId));
        }
    }
}
