package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

import java.awt.*;
import java.util.*;
import java.util.List;

public class JTSToGeneratorConverter {
    // Takes in empty lists of vertices, segments, polygons, and centroids. Also takes a list of JTS polygon data (generated via voronoi diagram) and populates the empty geometry lists with the JTS data.
    public void convertAllData(List<Geometry> polygonsJTS, Map<Integer, Vertex> vertices, Map<Integer, Segment> segments, Map<Integer, Polygon> polygons, List<Centroid> centroids) {
        JTSDataConversion(polygonsJTS, vertices, segments, polygons, centroids);
    }

    private void JTSDataConversion(List<Geometry> polygonsJTS, Map<Integer, Vertex> vertices, Map<Integer, Segment> segments, Map<Integer, Polygon> polygons, List<Centroid> centroids) {
        int vertexCounter = 0;
        int segCounter = 0;
        int polyCounter = 0;

        VertexSet set = new VertexSet(0.01);
        for (Geometry polygon : polygonsJTS) {
            // adds vertices and segments of the polygon to the vertex and segment list
            Coordinate[] coords = polygon.getCoordinates();

            List<Segment> polySegments = new ArrayList<>();
            for (int i=0; i<coords.length-1; i++) {
                Vertex v1 = new Vertex(coords[i].getX(), coords[i].getY());
                Vertex v2 = new Vertex(coords[i+1].getX(), coords[i+1].getY());
                boolean containedV1 = set.contains(v1);
                boolean containedV2 = set.contains(v2);
                Integer id1 = set.add(v1);
                Integer id2 = set.add(v2);

                if (!containedV1) {
                    v1.setId(id1);
                    vertices.put(id1, v1);
                    vertexCounter++;
                }

                if (!containedV2) {
                    v2.setId(id2);
                    vertices.put(id2, v2);
                    vertexCounter++;
                }

                Segment newSeg = new Segment(set.getVertex(id1), set.getVertex(id2));
                segments.put(segCounter, newSeg);
                polySegments.add(newSeg);
                segCounter++;
            }


            // get centroid
            org.locationtech.jts.algorithm.Centroid centroidJTS = new org.locationtech.jts.algorithm.Centroid(polygon);
            Polygon newPolygon = new Polygon(polyCounter, polySegments, Color.BLACK, 2f);
            Centroid newCentroid = new Centroid(vertexCounter, centroidJTS.getCentroid().getX(), centroidJTS.getCentroid().getY());
            centroids.add(newCentroid);
            Integer id = set.add(newCentroid);
            vertices.put(id, newCentroid);
            vertexCounter++;
            newPolygon.setCentroid(newCentroid);
            polygons.put(polyCounter, newPolygon);
            polyCounter++;
        }
    }
}
