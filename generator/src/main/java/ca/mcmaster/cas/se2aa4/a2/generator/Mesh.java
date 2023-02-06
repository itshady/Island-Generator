package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Mesh {
    private final int width = 500;
    private final int height = 500;
    private final double precision = 0.01;
    private final long matrixWidth = Math.round(width/precision);
    private final long matrixHeight = Math.round(height/precision);
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
        Map<Long, List<Long>> coords = new HashMap<>();
        //List<List<Integer>> matrix = initializeMatrix(coords);
        Map<Long, Vertex> vertices = initializeSquareVertices(coords);
//        for (Long num : vertices.keySet()) {
//            if (vertices.get(num).getY() == 20.0)
//            System.out.println(num+" ("+vertices.get(num).getX()+","+vertices.get(num).getY()+")");
//        }
        Map<Integer, Segment> segments = initializeSquareSegments(vertices);

        // Mesh handle both rudimentary conversions
        // Mesh handles whether classes get 802 or 8.02 (precision handling)
        // Mesh should have functions that define standards maps

        Set<Structs.Vertex> rudimentaryVertecies = extractLameVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = extractLameSegments(segments);
        mesh = Structs.Mesh.newBuilder().addAllVertices(rudimentaryVertecies).addAllSegments(rudimentarySegments).build();
    }

    private Map<Integer, Segment> initializeSquareSegments(Map<Long, Vertex> vertices) {
        Map<Integer, Segment> segments = new HashMap<>();
        Integer counter = 0;
        for (int i=0; i<matrixHeight; i+=square_size) {
            for (int j=0; j<matrixWidth-square_size; j+=square_size) {

                Long currPos = i*(matrixWidth)+j;
                Long nextPos = i*(matrixWidth)+j+square_size;
                Vertex currVertex = vertices.get(currPos);
                Vertex nextVertex = vertices.get(nextPos);
//                System.out.print("("+i+","+j+") ");
//                System.out.print(currVertex.getVertex()+" ");
//                System.out.print(nextVertex.getVertex());
//                System.out.println();

                segments.put(counter, new Segment(currVertex, nextVertex, 3f));
                counter++;
            }
//            System.out.println("NEW ROW");
        }

//        System.out.println("HEREEEEE");

        for (int i=0; i<matrixWidth; i+=square_size) {
            for (int j=0; j<matrixHeight-square_size; j+=square_size) {
                Long currPos = j*(matrixWidth)+i;
                Long nextPos = (j+square_size)*(matrixWidth)+i;
                Vertex currVertex = vertices.get(currPos);
                Vertex nextVertex = vertices.get(nextPos);

//                System.out.print("("+i+","+j+","+currPos+","+nextPos+") ");
//                System.out.print(currVertex.getVertex()+" ");
//                System.out.print(nextVertex.getVertex());
//                System.out.println();

                segments.put(counter, new Segment(currVertex, nextVertex));
                counter++;
            }
//            System.out.println("NEW COL");
        }

//        System.out.println("HEREEEEE 2");
        return segments;
    }

    private Map<Long, Vertex> initializeSquareVertices(Map<Long, List<Long>> coords) {
        Map<Long, Vertex> vertices = new HashMap<>();

        int counter = 0;
        for (int i=0; i<matrixHeight; i+=square_size) {
            for (int j=0; j<matrixWidth; j+=square_size) {
                Long pos = i*(matrixWidth)+j;

                if (!coords.containsKey(pos)) {
                    List<Long> xy = new ArrayList<>();
                    xy.add((long)j);
                    xy.add((long)i);
                    coords.put(pos, xy);
                    vertices.put(pos, new Vertex(xy.get(0),xy.get(1), new Color(counter%4 == 0 ? 255 : 0,0,0) ,counter%4 == 0 ? 3f : 6f ));
                    //vertices.put(pos, new Vertex(xy.get(0),xy.get(1), 4f));
                    counter++;
                }
                System.out.println("i: "+i+" j: "+j+"("+pos+", "+coords.get(pos)+")");
            }
        }
        return vertices;
    }

    private Set<Structs.Vertex> extractLameVertices(Map<Long, Vertex> vertices) {
        Set<Structs.Vertex> lameSet = new LinkedHashSet<>();
        for (Vertex vertex : vertices.values()) {
            lameSet.add(vertex.getVertex());
        }
        return lameSet;
    }

    private Set<Structs.Segment> extractLameSegments(Map<Integer, Segment> segments) {
        Set<Structs.Segment> lameSet = new LinkedHashSet<>();
        for (Segment segment : segments.values()) {
            lameSet.add(segment.getSegment());
        }
        return lameSet;
    }

}
