package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

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

        Map<Integer, Vertex> vertices = initializeSquareVertices(coords);
        Map<Integer, Segment> segments = initializeSquareSegments(vertices);
        Map<Integer, Polygon> polygons = initializeSquarePolygons(segments, vertices);

        // Mesh handle both rudimentary conversions
        // Mesh handles whether classes get 802 or 8.02 (precision handling)
        // Mesh should have functions that define standards maps

        Set<Structs.Vertex> rudimentaryVertices = extractLameVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = extractLameSegments(segments);
        Set<Structs.Polygon> rudimentaryPolygons = extractLamePolygons(polygons);
        mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).addAllPolygons(rudimentaryPolygons).build();
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
            Polygon newPolygon = new Polygon(segmentList, Color.BLACK, 2.2f, allCoords);
            newPolygon.generatePolygon();
            polygons.put(counter, newPolygon);
            counter++;
        }
        return polygons;
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

                segments.put(counter, new Segment(currVertex, nextVertex, (float) 0.5));
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

                segments.put(counter, new Segment(currVertex, nextVertex));
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

    private Set<Structs.Vertex> extractLameVertices(Map<Integer, Vertex> vertices) {
        Set<Structs.Vertex> lameSet = new LinkedHashSet<>();
        int counter = 0;
        for (Vertex vertex : vertices.values()) {
            vertex.setId(counter);
            lameSet.add(vertex.getVertex());
            counter++;
        }
        return lameSet;
    }

    private Set<Structs.Segment> extractLameSegments(Map<Integer, Segment> segments) {
        Set<Structs.Segment> lameSet = new LinkedHashSet<>();
        int counter = 0;
        for (Segment segment : segments.values()) {
            segment.setId(counter);
            segment.generateSegment();
            lameSet.add(segment.getSegment());
            counter++;
        }
        return lameSet;
    }

    private Set<Structs.Polygon> extractLamePolygons(Map<Integer, Polygon> polygons) {
        Set<Structs.Polygon> lameSet = new LinkedHashSet<>();
        for (Polygon polygon: polygons.values()) {
            polygon.generatePolygon();
            lameSet.add(polygon.getPolygon());
        }
        return lameSet;
    }

}
