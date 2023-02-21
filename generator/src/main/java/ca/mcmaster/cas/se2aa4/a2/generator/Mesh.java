package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Coordinate;

import java.util.*;
import java.util.List;

public abstract class Mesh {
    protected final int width = 500;
    protected final int height = 500;
    protected final int square_size = 20;
    private final double precision = 0.01;

    Structs.Mesh mesh;
    private List<Centroid> centroids;

    public Structs.Mesh generate() {
        generateDiagram(generatePoints());
        return mesh;
    }

    // coordsList is a list of vertices to build the voronoi diagram around
    protected void generateDiagram(List<Coordinate> coordsList) {
        List<Geometry> polygonsJTS = new VoronoiDiagram(width, height, precision).getVoronoiDiagram(coordsList);
        // Initialize maps to store all the data
        Map<Integer, Vertex> vertices = new LinkedHashMap<>();
        Map<Integer, Segment> segments = new LinkedHashMap<>();
        Map<Integer, Polygon> polygons = new LinkedHashMap<>();
        centroids = new ArrayList<>();

        // convert the JTS Geometries from voronoi diagram generator to our Geometries
        new JTSToGeneratorConverter().convertAllData(polygonsJTS, vertices, segments, polygons, centroids);

        // Add Neighbours
        new NeighbourCalculator().addNeighbours(polygonsJTS, polygons, centroids);

//        for (Segment segment : segments.values()) {
////            int avgColorRed = (segment.getV1().getColor().getRed()+segment.getV2().getColor().getRed())/2;
////            System.out.println(avgColorRed + "  " + segment.getColor().getRed());
////            if (avgColorRed != segment.getColor().getRed())
////                System.out.println(segment.getV1().getColor() + "  " + segment.getV2().getColor() + "  " + segment.getColor());
//
//        }

        // Convert all our geometries into the io ones
        GeneratorToStructsConverter converter = new GeneratorToStructsConverter();

        Set<Structs.Vertex> rudimentaryVertices = converter.convertVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = converter.convertSegments(segments);
        Set<Structs.Polygon> rudimentaryPolygons = converter.convertPolygons(polygons);
        //System.out.println(rudimentaryVertices.size() + "  " + vertices.size());
      mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).addAllPolygons(rudimentaryPolygons).build();
    }

    public List<Centroid> getCentroids() {
        return centroids;
    }

    protected abstract List<Coordinate> generatePoints();
}
