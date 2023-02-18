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
        // Create GeometryFactory to get voronoi diagram later
        GeometryFactory geometryFactory = new GeometryFactory();
        // Helps library create polygon by using MultiPoints
        MultiPoint points = geometryFactory.createMultiPointFromCoords(coordsList.toArray(new Coordinate[coordsList.size()]));
        // Create a boundary envelope
        Envelope envelope = new Envelope(0, width, 0, height);

        // Initialize voronoi diagram builder
        Geometry clippedDiagram = createVoronoiDiagram(geometryFactory, points, envelope);

        // Get all generated Polygons from the voronoi diagram builder
        List<Geometry> polygonsJTS = new ArrayList<>();
        for (int i=0; i<clippedDiagram.getNumGeometries(); i++) {
            polygonsJTS.add(clippedDiagram.getGeometryN(i));
        }

        // Initialize maps to store all the data
        Map<Integer, Vertex> vertices = new LinkedHashMap<>();
        Map<Integer, Segment> segments = new LinkedHashMap<>();
        Map<Integer, Polygon> polygons = new LinkedHashMap<>();
        List<Centroid> centroids = new ArrayList<>();

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

        //NEIGHBOURS
        List<Coordinate> c_coordList = new ArrayList<>();
        for (Centroid c : centroids) {
            Coordinate c_coord = new Coordinate(c.getX(), c.getY());
            c_coordList.add(c_coord);
        }

        GeometryFactory centroidFactory = new GeometryFactory();
        MultiPoint cPoints = centroidFactory.createMultiPointFromCoords(c_coordList.toArray(new Coordinate[coordsList.size()]));
        DelaunayTriangulationBuilder delaunayTriangulation = new DelaunayTriangulationBuilder();
        delaunayTriangulation.setSites(cPoints);
        Geometry triangulation = delaunayTriangulation.getEdges(centroidFactory);

        Map<Polygon, Set<Polygon>> neighbours = new LinkedHashMap<>();

        for (int i = 0; i < polygonsJTS.size(); i++) {
            Geometry polygon = polygonsJTS.get(i);
            Polygon ourP1 = polygons.get(i);
            Coordinate centroid = polygon.getCentroid().getCoordinate();
            Set<Polygon> currentNeighbour = new HashSet<>();
            for (int j = 0; j < triangulation.getNumGeometries(); j++) {
                List<Coordinate> currentTriangleCoords = Arrays.stream(triangulation.getGeometryN(j).getCoordinates()).toList();
                if (currentTriangleCoords.contains(centroid)) {
                    for (int k = 0; k < currentTriangleCoords.size(); k++) {
                        Coordinate neighbourCentroid = currentTriangleCoords.get(k);
                        Geometry p2 = polygonsJTS.get(polygonIndexFromCentroidCoord(neighbourCentroid, polygons));
                        Polygon ourP2 = polygons.get(polygonIndexFromCentroidCoord(neighbourCentroid, polygons));
                        if (!centroid.equals(neighbourCentroid) && (polygon.intersection(p2) instanceof LineString)) {
                            currentNeighbour.add(ourP2);
                        }
                    }
                }
            }
            neighbours.put(polygons.get(i), currentNeighbour);
        }

        for (Polygon p: neighbours.keySet()) {
            p.addPolygonNeighbourSet(neighbours.get(p));
            //System.out.println(p.getId() + "-" + p.getPolygonNeighbours());
        }

        GeneratorToStructsConverter converter = new GeneratorToStructsConverter();

        Set<Structs.Vertex> rudimentaryVertices = converter.convertVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = converter.convertSegments(segments);
        Set<Structs.Polygon> rudimentaryPolygons = converter.convertPolygons(polygons);

      mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).addAllPolygons(rudimentaryPolygons).build();
    }

    private Integer polygonIndexFromCentroidCoord(Coordinate centroidCoord, Map<Integer, Polygon> polygons) {
        for (Integer id : polygons.keySet()) {
            Coordinate currentCoord = new Coordinate(polygons.get(id).getCentroid().getX(), polygons.get(id).getCentroid().getY());
            if (currentCoord.equals(centroidCoord)) {
                return id;
            }
        }
        return -1;
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
