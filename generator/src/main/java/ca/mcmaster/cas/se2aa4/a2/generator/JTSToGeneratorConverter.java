package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JTSToGeneratorConverter {
    // Takes in empty lists of vertices, segments, polygons, and centroids. Also takes a list of JTS polygon data (generated via voronoi diagram) and populates the empty geometry lists with the JTS data.
    public void convertAllData(List<Geometry> polygonsJTS, Map<Integer, Vertex> vertices, Map<Integer, Segment> segments, Map<Integer, Polygon> polygons, List<Centroid> centroids) {
        JTSDataConversion(polygonsJTS, vertices, segments, polygons, centroids);
    }

    private void JTSDataConversion(List<Geometry> polygonsJTS, Map<Integer, Vertex> vertices, Map<Integer, Segment> segments, Map<Integer, Polygon> polygons, List<Centroid> centroids) {
        int vertexCounter = 0;
        int segCounter = 0;
        int polyCounter = 0;
        for (Geometry polygon : polygonsJTS) {
            // adds vertices of the polygon to the vertex list
            Coordinate[] coords = polygon.getCoordinates();
            int startCounter = vertexCounter;
            for (Coordinate coord : coords) {
                vertices.put(vertexCounter, new Vertex(vertexCounter, coord.getX(), coord.getY()));
                vertexCounter++;
            }

            // create and add segments to polygon
            List<Segment> polySegments = new ArrayList<>();
            for (int i=startCounter; i<vertexCounter; i++) {
                int nextIndex = ((i+1)-startCounter)%(vertexCounter-startCounter) + startCounter;
                Segment newSeg = new Segment(segCounter, vertices.get(i), vertices.get(nextIndex));
                segments.put(segCounter, newSeg);
                polySegments.add(newSeg);
                segCounter++;
            }

            // get centroid
            org.locationtech.jts.algorithm.Centroid centroidJTS = new org.locationtech.jts.algorithm.Centroid(polygon);
            Polygon newPolygon = new Polygon(polyCounter, polySegments, Color.BLACK, 1f);
            Centroid newCentroid = new Centroid(vertexCounter, centroidJTS.getCentroid().getX(), centroidJTS.getCentroid().getY());
            centroids.add(newCentroid);
            vertices.put(vertexCounter, newCentroid);
            vertexCounter++;
            newPolygon.setCentroid(newCentroid);
            polygons.put(polyCounter, newPolygon);
            polyCounter++;
        }
    }
}
