package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Centroid;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.generator.Helpers.GeneratorToStructsConverter;
import ca.mcmaster.cas.se2aa4.a2.generator.Helpers.JTSToGeneratorConverter;
import ca.mcmaster.cas.se2aa4.a2.generator.Helpers.NeighbourCalculator;
import ca.mcmaster.cas.se2aa4.a2.generator.Helpers.VoronoiDiagram;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Coordinate;

import java.util.*;
import java.util.List;

public abstract class Mesh implements GeometryDiagram {
    protected final int width = 500;
    protected final int height = 500;
    private final double precision = 0.01;

    Structs.Mesh mesh;
    private List<Centroid> centroids;

    /**
     * calls function to generate the Voronoi Diagram
     * @return Structs.Mesh generated by the previous call
     */
    public Structs.Mesh generate() {
        generateDiagram(generatePoints());
        return mesh;
    }

    /**
     * Generates the Voronoi diagram and puts it into the mesh attribute
     * @param coordsList: List of coordinates to build the voronoi diagram around
     */
    protected void generateDiagram(List<Coordinate> coordsList) {
        ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Coordinate.precision = precision;
        List<Geometry> polygonsJTS = new VoronoiDiagram(width, height, precision).getVoronoiDiagram(coordsList);

        centroids = new ArrayList<>();

        // convert the JTS Geometries from voronoi diagram generator to our Geometries
        JTSToGeneratorConverter JTSconverter = new JTSToGeneratorConverter();
        JTSconverter.convertAllData(polygonsJTS);
        centroids = JTSconverter.getCentroids();

        // Add Neighbours
        new NeighbourCalculator().addNeighbours(polygonsJTS, JTSconverter.getPolygons(), centroids);

        // Convert all our geometries into the io ones
        GeneratorToStructsConverter converter = new GeneratorToStructsConverter();

        Set<Structs.Vertex> rudimentaryVertices = converter.convertVertices(JTSconverter.getVertices());
        Set<Structs.Segment> rudimentarySegments = converter.convertSegments(JTSconverter.getSegments());
        Set<Structs.Polygon> rudimentaryPolygons = converter.convertPolygons(JTSconverter.getPolygons());

        mesh = Structs.Mesh.newBuilder()
                .addAllVertices(rudimentaryVertices)
                .addAllSegments(rudimentarySegments)
                .addAllPolygons(rudimentaryPolygons)
                .build();
    }

    /**
     * Gets the centroids of the generated voronoi diagram
     * @return List<Centroid>
     */
    public List<Centroid> getCentroids() {
        return centroids;
    }

    /**
     * Generate a list of points to generate the voronoi diagram around
     * @return List of points
     */
    protected abstract List<Coordinate> generatePoints();
}