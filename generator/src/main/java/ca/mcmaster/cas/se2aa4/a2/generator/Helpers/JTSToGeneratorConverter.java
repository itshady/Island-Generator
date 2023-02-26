package ca.mcmaster.cas.se2aa4.a2.generator.Helpers;

import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.GeometrySet;
import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.PolygonSet;
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
    private final VertexSet vertexSet = new VertexSet();
    private final SegmentSet segmentSet = new SegmentSet();
    private final PolygonSet polygonSet = new PolygonSet();
    private List<Centroid> centroids = new ArrayList<>();

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

    // Takes in empty lists of vertices, segments, polygons, and centroids. Also takes a list of JTS polygon data (generated via voronoi diagram) and populates the empty geometry lists with the JTS data.
    public void convertAllData(List<Geometry> polygonsJTS) {
        JTSDataConversion(polygonsJTS);
    }

    private void JTSDataConversion(List<Geometry> polygonsJTS) {
        for (Geometry polygon : polygonsJTS) {
            // adds vertices and segments of the polygon to the vertex and segment list
            Coordinate[] coords = polygon.getCoordinates();
            List<Segment> polySegments = new ArrayList<>();
            addVerticesAndSegments(coords, polySegments);

            // get centroid
            addPolygon(polygon, polySegments);
        }
    }

    private void addPolygon(Geometry polygon, List<Segment> polySegments) {
        Polygon newPolygon = new Polygon(polySegments, Color.BLACK, 2f);
        Integer polyId = polygonSet.add(newPolygon);
        newPolygon.setId(polyId);
        addCentroid(polygon, newPolygon);
    }

    private void addCentroid(Geometry polygon, Polygon generatorPolygon) {
        org.locationtech.jts.algorithm.Centroid centroidJTS = new org.locationtech.jts.algorithm.Centroid(polygon);
        Centroid newCentroid = new Centroid(centroidJTS.getCentroid().getX(), centroidJTS.getCentroid().getY());
        generatorPolygon.setCentroid(newCentroid);
        centroids.add(newCentroid);
        Integer id = vertexSet.add(newCentroid);
    }

    private void addVerticesAndSegments(Coordinate[] coords, List<Segment> polySegments) {
        for (int i = 0; i< coords.length-1; i++) {
            Vertex v1 = new Vertex(coords[i].getX(), coords[i].getY());
            Vertex v2 = new Vertex(coords[i+1].getX(), coords[i+1].getY());
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
