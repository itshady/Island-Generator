package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Mesh {
    private final int width = 500;
    private final int height = 500;
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
        //List<List<Integer>> matrix = initializeMatrix(coords);
        Map<Integer, Vertex> vertices = initializeSquareVertices(coords);
        Map<Integer, Segment> segments = initializeSquareSegments(vertices);

        // Mesh handle both rudimentary conversions
        // Mesh handles whether classes get 802 or 8.02 (precision handling)
        // Mesh should have functions that define standards maps

        Set<Structs.Vertex> rudimentaryVertices = extractLameVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = extractLameSegments(segments);
        mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertices).addAllSegments(rudimentarySegments).build();
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

                segments.put(counter, new Segment(currVertex, nextVertex, (float) 3));
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
                    vertices.put(pos, new Vertex(pos, xy.get(0),xy.get(1), new Color(counter%4 == 0 ? 255 : 0,0,0) ,counter%4 == 0 ? 3f : 6f ));
                    //vertices.put(pos, new Vertex(xy.get(0),xy.get(1), 4f));
                    counter++;
                }
                System.out.println("i: "+i+" j: "+j+"("+pos+", "+coords.get(pos)+")");
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
        for (Segment segment : segments.values()) {
            segment.generateSegment();
            lameSet.add(segment.getSegment());
        }
        return lameSet;
    }

}
