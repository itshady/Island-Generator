package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

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
        Map<Integer, List<Integer>> coords = new HashMap<>();

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
        Coordinate coord = new Coordinate(20000, 20000);
        coordList.add(coord);
        Vertex poly1 = new Vertex(4, 20000,20000);
        Vertex poly2 = new Vertex(5, 30000,30000);
        Coordinate coord2 = new Coordinate(30000, 30000);
        coordList.add(coord2);
        // Create GeometryFactory to get voronoi diagram later
        GeometryFactory geometryFactory = new GeometryFactory();
        // Helps library create polygon by using MultiPoints
        MultiPoint points = geometryFactory.createMultiPointFromCoords(coordList.toArray(new Coordinate[coordList.size()]));

        // Initialize voronoi diagram builder
        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();
        voronoi.setSites(points); // Sets the vertices that will be diagrammed
        // creates the polygon vertices around generated sites
        Geometry diagram = voronoi.getDiagram(geometryFactory);
        Geometry polygon1 = diagram.getGeometryN(0);  // chooses first polygon
        System.out.println(Arrays.toString(polygon1.getCoordinates()));

        // Make vertices from coordinates of polygon
        Vertex a1 = new Vertex(0, (polygon1.getCoordinates()[0]).getX(), (polygon1.getCoordinates()[0]).getY());
        Vertex a2 = new Vertex(1, (polygon1.getCoordinates()[1]).getX(), (polygon1.getCoordinates()[1]).getY());
        Vertex x = new Vertex(1, 0,0);
        Vertex x2 = new Vertex(1, 50000,50000);

        Map<Integer, Vertex> vertices = new HashMap<>();
        vertices.put(0, a1);
        vertices.put(1, a2);
        vertices.put(2,x);
        vertices.put(3,x2);
        vertices.put(4,poly1);
        vertices.put(5,poly2);

        // make segment out of vertices
        Segment a = new Segment(a1, a2);
        Map<Integer, Segment> segments = new HashMap<>();
        segments.put(0, a);

        Set<Structs.Vertex> rudimentaryVertices = extractVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = extractSegments(segments);

//      mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).addAllPolygons(rudimentaryPolygons).build();
        mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).build();
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
