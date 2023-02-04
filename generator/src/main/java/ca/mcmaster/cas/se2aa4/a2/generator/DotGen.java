package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;


public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final double precision = 1;
    private final int matrixWidth = (int) (width/precision);
    private final int matrixHeight = (int) (height/precision);
    private final int square_size = (int) (20/precision);

    public Mesh generate() {
        // map out points and store id in segment
        // depth first search
        // 1 2 3 4
        // 5 6 7 8
        // list is <x,y>
        Map<Integer, List<Integer>> coords = new HashMap<>();
        System.out.println("HERE 1");
        List<List<Integer>> matrix = initializeMatrix(coords);
        System.out.println("HERE 2");
        Map<Integer, Vertex> vertices = new HashMap<>();
        System.out.println("HERE 3");
        //printMatrix(matrix);

//        for (int i=0; i<matrixHeight; i++) {
//            for (int j=0; j<matrixWidth; j++) {
//                Integer pos = i*(j+1)+j;
//                List<Integer> xy = coords.get(pos);
//                vertices.put(pos, new Vertex(xy.get(0), xy.get(1), new Color(0,0,0)));
//            }
//        }
        System.out.println("HERE 4");

        for (int i=0; i<matrixHeight; i+=square_size) {
            for (int j=0; j<matrixWidth; j+=square_size) {
                Integer pos = i*(matrixWidth)+j;
                //System.out.println("i: "+i+" j: "+j+"("+pos+", "+coords.get(pos)+")");
                List<Integer> xy = coords.get(pos);
                vertices.put(pos, new Vertex(xy.get(0),xy.get(1), new Color(0,0,0)));
            }
            //System.out.println("---------------ROW "+i+"----------------");
        }
        System.out.println("HERE 5");

        // Map<> verticesMap = new LinkedHashMap<>();
        //Set<Vertex> vertices = createColorVertices(verticesMap);
//        Set<Segment> segments = createColorSegments(vertices);
        Set<Structs.Vertex> toRudimentaryVertex = extractLameVertices(vertices);
        return Mesh.newBuilder().addAllVertices(toRudimentaryVertex).build();
    }

    private List<List<Integer>> initializeMatrix(Map<Integer, List<Integer>> coords) {
        List<List<Integer>> matrix = new ArrayList<List<Integer>>(matrixHeight*matrixWidth);
        Integer counter = 0;
        for (Integer i=0; i<matrixHeight; i++) {
            List<Integer> row = new ArrayList<>();
            matrix.add(row);
            for (Integer j=0; j<matrixWidth; j++) {
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

    private Set<Structs.Vertex> extractLameVertices(Map<Integer, Vertex> vertices) {
        Set<Structs.Vertex> lameSet = new LinkedHashSet<>();
        for (Vertex vertex : vertices.values()) {
            lameSet.add(vertex.getVertex());
        }
        return lameSet;
    }
}
