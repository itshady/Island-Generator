package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.algorithm.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class Mesh {
    protected final int width = 500;
    protected final int height = 500;
    protected final double precision = 1;
    protected final int matrixWidth = (int) Math.round(width/precision);
    protected final int matrixHeight = (int) Math.round(height/precision);
    protected final int square_size = (int) (20/precision);

    Structs.Mesh mesh;

    public Structs.Mesh generate() {
        generateInternal();
        return mesh;
    }
    public void generateInternal() {
        // map out points and store id in segment
        // depth first search

        // TESTING FOR VORONOI DIAGRAM
        // Initialize a list of "randomly" generated coordinates
        List<Coordinate> coordsList = generatePoints();

        // Create GeometryFactory to get voronoi diagram later
        GeometryFactory geometryFactory = new GeometryFactory();
        // Helps library create polygon by using MultiPoints
        MultiPoint points = geometryFactory.createMultiPointFromCoords(coordsList.toArray(new Coordinate[coordsList.size()]));

        // Create a boundary envelope
        Envelope envelope = new Envelope(0, width, 0, height);

        // Initialize voronoi diagram builder
        Geometry clippedDiagram = createVoronoiDiagram(geometryFactory, points, envelope);

        List<Geometry> polygonsJTS = new ArrayList<>();
        for (int i=0; i<clippedDiagram.getNumGeometries(); i++) {
            polygonsJTS.add(clippedDiagram.getGeometryN(i));
        }

        Map<Integer, Vertex> vertices = new LinkedHashMap<>();
        Map<Integer, Segment> segments = new LinkedHashMap<>();
        Map<Integer, Polygon> polygons = new LinkedHashMap<>();

        int counter = 0;
        int segCounter = 0;
        int polyCounter = 0;
        for (Geometry polygon : polygonsJTS) {
            Coordinate[] coords = polygon.getCoordinates();
            // adds vertices of the polygon to the vertex list
            int startCounter = counter;
            for (Coordinate coord : coords) {
                vertices.put(counter, new Vertex(counter, coord.getX(), coord.getY()));
                counter++;
            }
            // create and add segments to polygon
            List<Segment> polySegments = new ArrayList<>();
            for (int i=startCounter; i<counter; i++) {
                int nextIndex = ((i+1)-startCounter)%(counter-startCounter) + startCounter;
                Segment newSeg = new Segment(segCounter, vertices.get(i), vertices.get(nextIndex));
                segments.put(segCounter, newSeg);
                polySegments.add(newSeg);
                segCounter++;
            }

            // get centroid
            org.locationtech.jts.algorithm.Centroid centroidJTS = new org.locationtech.jts.algorithm.Centroid(polygon);
            Polygon newPolygon = new Polygon(counter, polySegments, Color.BLACK, 1f);
            Centroid newCentroid = new Centroid(counter, centroidJTS.getCentroid().getX(), centroidJTS.getCentroid().getY());
            vertices.put(counter, newCentroid);
            counter++;
            newPolygon.setCentroid(newCentroid);
            polygons.put(polyCounter, newPolygon);
            polyCounter++;
        }

        GeneratorToStructsConverter converter = new GeneratorToStructsConverter();

        Set<Structs.Vertex> rudimentaryVertices = converter.convertVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = converter.convertSegments(segments);
        Set<Structs.Polygon> rudimentaryPolygons = converter.convertPolygons(polygons);

      mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).addAllPolygons(rudimentaryPolygons).build();
    }

    private Geometry createVoronoiDiagram(GeometryFactory geometryFactory, MultiPoint points, Envelope envelope) {
        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();
        voronoi.setTolerance(1);
        voronoi.setSites(points); // Sets the vertices that will be diagrammed
        // creates the polygon vertices around generated sites
        Geometry diagram = voronoi.getDiagram(geometryFactory);

        // Clipped diagram to remove vertices outside height x width
        Geometry clippedDiagram = diagram.intersection(geometryFactory.toGeometry(envelope));
        return clippedDiagram;
    }

    protected abstract List<Coordinate> generatePoints();
}
