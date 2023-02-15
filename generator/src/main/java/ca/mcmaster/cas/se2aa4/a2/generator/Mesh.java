package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;
import org.locationtech.jts.algorithm.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Mesh {
    private final int width = 520;
    private final int height = 520;
    private final double precision = 0.01;
    private final int matrixWidth = (int) Math.round(width/precision);
    private final int matrixHeight = (int) Math.round(height/precision);
    private final int square_size = (int) (20/precision);

    Structs.Mesh mesh;

    public Mesh() {

    }

    public Structs.Mesh generateSquareMesh() {
        generate();
        return mesh;
    }
    public void generate() {
        // map out points and store id in segment
        // depth first search

        // list is <x,y>
//        Map<Integer, List<Integer>> coords = new HashMap<>();

        //Map<Integer, Vertex> vertices = initializeSquareVertices(coords);
        //Map<Integer, Segment> segments = initializeSquareSegments(vertices);
        //Map<Integer, Polygon> polygons = initializeSquarePolygons(segments, vertices);

//        for (Vertex vertex : vertices.values()) {
//            //if (vertex.isCentroid()) System.out.println(vertex.getId() + " x: " + vertex.getX() + "  y: " + vertex.getY());
//        }
        // Mesh handle both rudimentary conversions
        // Mesh handles whether classes get 802 or 8.02 (precision handling)
        // Mesh should have functions that define standards maps

        //Set<Structs.Vertex> rudimentaryVertices = extractVertices(vertices);
//        Set<Structs.Segment> rudimentarySegments = extractSegments(segments);
//        Set<Structs.Polygon> rudimentaryPolygons = extractPolygons(polygons);


        // TESTING FOR VORONOI DIAGRAM
        // Initialize a list of "randomly" generated coordinates
        List<Coordinate> coordList = new ArrayList<>();
        Random bag = new Random();
        int rangeMin = 0;
        int rangeMax = 500;
        for (int i=0; i<bag.nextInt(100,150); i++) {
            double rand1 = ((int)((rangeMin + (rangeMax - rangeMin) * bag.nextDouble())*100))/100.0;
            double rand2 = ((int)((rangeMin + (rangeMax - rangeMin) * bag.nextDouble())*100))/100.0;
            coordList.add(new Coordinate(rand1,rand2));
        }

        // Create GeometryFactory to get voronoi diagram later
        GeometryFactory geometryFactory = new GeometryFactory();
        // Helps library create polygon by using MultiPoints
        MultiPoint points = geometryFactory.createMultiPointFromCoords(coordList.toArray(new Coordinate[coordList.size()]));

        // Create a boundary envelope
        Envelope envelope = new Envelope(0, 500, 0, 500);

        // Initialize voronoi diagram builder
        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();
        voronoi.setSites(points); // Sets the vertices that will be diagrammed
        // creates the polygon vertices around generated sites
        Geometry diagram = voronoi.getDiagram(geometryFactory);

        // Clipped diagram to remove vertices outside height x width
        Geometry clippedDiagram = diagram.intersection(geometryFactory.toGeometry(envelope));

        List<Geometry> polygonsJTS = new ArrayList<>();
        for (int i=0; i<clippedDiagram.getNumGeometries(); i++) {
            polygonsJTS.add(clippedDiagram.getGeometryN(i));
        }

        Map<Integer, Vertex> vertices = new HashMap<>();
        Map<Integer, Segment> segments = new HashMap<>();
        Map<Integer, Polygon> polygons = new HashMap<>();

        int counter = 0;
        int segCounter = 0;
        int polyCounter = 0;
        for (Geometry polygon : polygonsJTS) {
            Coordinate[] coords = polygon.getCoordinates();
            // adds vertices of the polygon to the vertex list
            int startCounter = counter;
            for (Coordinate coord : coords) {
                vertices.put(counter, new Vertex(counter, coord.getX()*100, coord.getY()*100));
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
            Centroid newCentroid = new Centroid(counter, centroidJTS.getCentroid().getX()*100, centroidJTS.getCentroid().getY()*100);
            vertices.put(counter, newCentroid);
            counter++;
            newPolygon.setCentroid(newCentroid);
            polygons.put(polyCounter, newPolygon);
            polyCounter++;
        }

//        for (Coordinate centroids : coordList) {
//            vertices.put(counter, new Centroid(counter, centroids.getX()*100, centroids.getY()*100));
//            counter++;
//        }

        Set<Structs.Vertex> rudimentaryVertices = extractVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = extractSegments(segments);
        Set<Structs.Polygon> rudimentaryPolygons = extractPolygons(polygons);

      mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).addAllPolygons(rudimentaryPolygons).build();
//        mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).build();
    }

    private List<List<Double>> getCoordsForCentroid(List<Segment> segments, Map<Integer, Vertex> vertices) {
        List<List<Double>> coords = new ArrayList<>();

        for (int i = 0; i < segments.size(); i++) {
            int index;
            List<Double> xy = new ArrayList<>();
            segments.get(i).generateSegment();

            // Depending on which point, get either V1 or V2
            if (i == 1 || i == 2) {
                index = segments.get(i).getSegment().getV2Idx();
            } else {
                index = segments.get(i).getSegment().getV1Idx();
            }
            xy.add(vertices.get(index).getX());
            xy.add(vertices.get(index).getY());
            coords.add(xy);
        }
        return coords;
    }

    private Map<Integer, Polygon> initializeSquarePolygons(Map<Integer, Segment> segments, Map<Integer, Vertex> vertices) {
        Map<Integer, Polygon> polygons = new HashMap<>();
        Integer counter = 0;

        int width = matrixWidth / square_size;
        int height = matrixHeight / square_size;

        for (int i = 0; i < (width-1)*(height-1); i++) {
            List<Segment> segmentList = new ArrayList<>(4);
            segmentList.add(segments.get(i));
            segmentList.add(segments.get((i/(width-1)) + (width-1)*height + (i%(width-1))*(height-1)));
            segmentList.add(segments.get(i + (width-1)));
            segmentList.add(segments.get((i/(width-1)) + (width-1)*height + (i%(width-1))*(height-1) + (height-1)));

            // Obtain the points needed to calculate the centroid
            List<List<Double>> allCoords = getCoordsForCentroid(segmentList, vertices);
            Polygon newPolygon = new Polygon(counter, segmentList, Color.BLACK, 1f, allCoords);
            polygons.put(counter, newPolygon);
            vertices.put(newPolygon.getCentroidId(),newPolygon.getCentroid());
            counter++;

            // Determine Polygon Neighbours
            Map<Segment, List<Polygon>> polygonNeighbours = setPolygonNeighbours(polygons,segments);
            for (List<Polygon> list : polygonNeighbours.values()) {
                //System.out.println(list);
                for (Polygon p: list) {
                    p.addPolygonNeighbourSet(list);
                    p.removePolygonNeighbour(p);
                }
            }
        }
//        System.out.println("Polygon " + polygons.get(15).getId() + " - Neighbours: " + polygons.get(15).getPolygonNeighbours());
//        System.out.println("Polygon " + polygons.get(16).getId() + " - Neighbours: " + polygons.get(16).getPolygonNeighbours());
        return polygons;
    }

    private Map<Segment, List<Polygon>> setPolygonNeighbours(Map<Integer, Polygon> polygons, Map<Integer, Segment> segments) {
        Map<Segment, List<Polygon>> polygonsAttachedToSegment = new HashMap<>();
        for (Integer s : segments.keySet()) {
            List<Polygon> attachedPolygons = new ArrayList<>();
            for (Integer p : polygons.keySet()) {
                if (polygons.get(p).getSegmentList().contains(segments.get(s))) {
                    attachedPolygons.add(polygons.get(p));
                }
            }
            polygonsAttachedToSegment.put(segments.get(s), attachedPolygons);
        }
        return polygonsAttachedToSegment;
    }


    private Map<Integer, Segment> initializeSquareSegments(Map<Integer, Vertex> vertices) {
        Map<Integer, Segment> segments = new HashMap<>();
        Integer counter = 0;

        // horizontal segments
        for (int i=0; i<matrixHeight; i+=square_size) {
            for (int j=0; j<matrixWidth-square_size; j+=square_size) {
                Integer currPos = i*(matrixWidth)+j;
                Integer nextPos = i*(matrixWidth)+j+square_size;
                Vertex currVertex = vertices.get(currPos);
                Vertex nextVertex = vertices.get(nextPos);

                segments.put(counter, new Segment(currVertex, nextVertex, 0.5f));
                counter++;
            }
        }
        // vertical segments
        for (int i=0; i<matrixWidth; i+=square_size) {
            for (int j=0; j<matrixHeight-square_size; j+=square_size) {
                Integer currPos = j*(matrixWidth)+i;
                Integer nextPos = (j+square_size)*(matrixWidth)+i;
                Vertex currVertex = vertices.get(currPos);
                Vertex nextVertex = vertices.get(nextPos);

                segments.put(counter, new Segment(currVertex, nextVertex, 0.5f));
                counter++;
            }
        }

        return segments;
    }

    private Map<Integer, Vertex> initializeSquareVertices(Map<Integer, List<Integer>> coords) {
        Map<Integer, Vertex> vertices = new HashMap<>();

        int counter = 0;
        for (int i=0; i<matrixHeight; i+=square_size) {
            for (int j=0; j<matrixWidth; j+=square_size) {
                Integer pos = i*(matrixWidth)+j;

                if (!coords.containsKey(pos)) {
                    List<Integer> xy = new ArrayList<>();
                    xy.add(j);
                    xy.add(i);
                    coords.put(pos, xy);
                    //vertices.put(pos, new Vertex(pos, xy.get(0),xy.get(1), new Color(counter%4 == 0 ? 255 : 0,0,0) , 3f));
                    vertices.put(pos, new Vertex(pos, xy.get(0),xy.get(1), 3f));
                    counter++;
                }
                //System.out.println("i: "+i+" j: "+j+"("+pos+", "+coords.get(pos)+")");
            }
        }
        return vertices;
    }

    private Set<Structs.Vertex> extractVertices(Map<Integer, Vertex> vertices) {
        Set<Structs.Vertex> vertexSet = new LinkedHashSet<>();
        int counter = 0;
        for (Vertex vertex : vertices.values()) {
            vertex.setId(counter);
            vertexSet.add(vertex.getVertex());
            counter++;
        }
        return vertexSet;
    }

    private Set<Structs.Segment> extractSegments(Map<Integer, Segment> segments) {
        Set<Structs.Segment> segmentSet = new LinkedHashSet<>();
        int counter = 0;
        for (Segment segment : segments.values()) {
            segment.setId(counter);
            segment.generateSegment();
            segmentSet.add(segment.getSegment());
            counter++;
        }
        return segmentSet;
    }

    private Set<Structs.Polygon> extractPolygons(Map<Integer, Polygon> polygons) {
        Set<Structs.Polygon> polygonSet = new LinkedHashSet<>();
        for (Polygon polygon: polygons.values()) {
            polygon.generatePolygon();
            polygonSet.add(polygon.getPolygon());
        }
        return polygonSet;
    }

}
