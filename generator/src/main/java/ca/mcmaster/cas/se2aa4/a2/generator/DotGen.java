package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;


public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final double precision = 0.01;
    private final long matrixWidth = Math.round(width/precision);
    private final long matrixHeight = Math.round(height/precision);
    private final int square_size = (int) (20/precision);

    public Mesh generate() {
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

        Set<Structs.Vertex> rudimentaryVertecies = extractLameVertices(vertices);
        Set<Structs.Segment> rudimentarySegments = extractLameSegments(segments);
        return Mesh.newBuilder().addAllVertices(rudimentaryVertecies).addAllSegments(rudimentarySegments).build();
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
                    vertices.put(pos, new Vertex(xy.get(0),xy.get(1), new Color(counter%2 == 0 ? 255 : 0,0,0) ,counter%2 == 0 ? 3f : 6f ));
                    //vertices.put(pos, new Vertex(xy.get(0),xy.get(1), 4f));
                    counter++;
                }
                System.out.println("i: "+i+" j: "+j+"("+pos+", "+coords.get(pos)+")");
            }
        }
        return vertices;
    }

    private List<List<Integer>> initializeMatrix(Map<Integer, List<Integer>> coords) {
        List<List<Integer>> matrix = new ArrayList<List<Integer>>((int) (matrixHeight*matrixWidth));
        Integer counter = 0;
        for (int i=0; i<matrixHeight; i++) {
            List<Integer> row = new ArrayList<>();
            matrix.add(row);
            for (int j = 0; j<matrixWidth; j++) {
                row.add(counter);
                List<Integer> xy = new ArrayList<>();
                xy.add(j);
                xy.add(i);
                coords.put(counter, xy);
                counter++;
            }
        }
        return matrix;
    }

    private static void printMatrix(List<List<Integer>> matrix) {
        for (List<Integer> row : matrix) {
            for (Integer cell : row) {
                System.out.print(""+cell+" ");
            }
            System.out.println();
        }
    }


//    private Set<Vertex> createColorVertices(Map<Integer, Vertex> verticesMap) {
//        Set<Vertex> vertices = new LinkedHashSet<>();
//        Integer counter = 0;
//        // Create all the vertices
//        for(int x = 0; x < width; x += square_size) {
//            for(int y = 0; y < height; y += square_size) {
//                Vertex newPoint = new Vertex(x, y, new Color(0,0,0));
//                vertices.add(newPoint);
//                verticesMap.put(counter, newPoint);
//                counter++;
//            }
//        }
//        return vertices;
//    }

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