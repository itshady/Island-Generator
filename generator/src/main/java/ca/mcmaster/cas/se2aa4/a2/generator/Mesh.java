package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

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
        // Initialize a list of coordinates to create the voronoi diagram around
        List<Coordinate> coordsList = generatePoints();
        List<Geometry> polygonsJTS = new VoronoiDiagram(width, height).getVoronoiDiagram(coordsList);

        // Initialize maps to store all the data
        Map<Integer, Vertex> vertices = new LinkedHashMap<>();
        Map<Integer, Segment> segments = new LinkedHashMap<>();
        Map<Integer, Polygon> polygons = new LinkedHashMap<>();
        List<Centroid> centroids = new ArrayList<>();

        // convert the JTS Geometries from voronoi diagram generator to our Geometries
        new JTSToGeneratorConverter().convertAllData(polygonsJTS, vertices, segments, polygons, centroids);

        // Add Neighbours
        new NeighbourCalculator().addNeighbours(polygonsJTS, polygons, centroids);

        // Convert all our geometries into the io ones
        GeneratorToStructsConverter converter = new GeneratorToStructsConverter();

        Set<Structs.Vertex> rudimentaryVertices = converter.convertVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = converter.convertSegments(segments);
        Set<Structs.Polygon> rudimentaryPolygons = converter.convertPolygons(polygons);

      mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).addAllPolygons(rudimentaryPolygons).build();
    }

    protected abstract List<Coordinate> generatePoints();
}
