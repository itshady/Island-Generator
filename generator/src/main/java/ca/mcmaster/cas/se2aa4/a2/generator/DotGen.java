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


//        Set<Vertex> vertices = createColorVertices();
//        Set<Segment> segments = createColorSegments(vertices);

        return Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).build();
    }

    private Segment createSegment(Vertex vertex1, Vertex vertex2, String colorStr) {
        Property v1 = Property.newBuilder().setKey("vertex1").setValue("["+vertex1.getX()+","+vertex1.getY()+"]").build();
        Property v2 = Property.newBuilder().setKey("vertex2").setValue("["+vertex2.getX()+","+vertex2.getY()+"]").build();
        Property color = Property.newBuilder().setKey("rgb_color").setValue(colorStr).build();
        return Segment.newBuilder().addProperties(v1).addProperties(v2).addProperties(color).build();
    }

    private ArrayList<String> generateColors(int numOfColors) {
        ArrayList<String> colors = new ArrayList<>();
        Random bag = new Random();
        for (int i=0; i<numOfColors; i++) {
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            Color color = new Color(red, green, blue);
            String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue();
            colors.add(colorStr);
        }
        return colors;
    }

    // Must be in "1,1,1" format
    // returns an Integer array like [1,1,1]
    private List<Integer> colorToInt(String color) {
        List<Integer> rgb;
        Integer[] primitiveRGB = Stream.of(color.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
        rgb = Arrays.asList(primitiveRGB);
        return rgb;
    }

    // averages two numbers, rounds down always
    private Integer average(Integer a, Integer b) {
        return (a+b)/2;
    }

    // takes 2 color string in "1,1,1" format and returns the average
    // average is the average of each element (R,G,B)
    private String averageColor(String color1, String color2) {
        List<Integer> color1Int = colorToInt(color1);
        List<Integer> color2Int = colorToInt(color2);

        //System.out.println("color1: "+color1Int+" - color2: "+color2Int);
        Integer averageRed = average(color1Int.get(0),color2Int.get(0));
        Integer averageGreen = average(color1Int.get(1),color2Int.get(1));
        Integer averageBlue = average(color1Int.get(2),color2Int.get(2));

        String avgColor = ""+averageRed+","+averageGreen+","+averageBlue;
        return avgColor;
    }

    private Set<Vertex> createColorVerticesWithSegments(Set<Segment> segments) {
        Set<Vertex> vertices = new LinkedHashSet<>();
        ArrayList<Vertex> previousVertex = new ArrayList<>();
        ArrayList<Property> previousColors = new ArrayList<>();
        ArrayList<Vertex> topVertex = new ArrayList<>();
        ArrayList<Property> topColors = new ArrayList<>();
        ArrayList<Vertex> bottomVertex = new ArrayList<>();
        ArrayList<Property> bottomColors = new ArrayList<>();
        int i = 0;
        // Create all the vertices with segments
        for(int x = 0; x < width; x += square_size*2) {

            for(int y = 0; y < height; y += square_size*2) {

                ArrayList<String> colorCodes = generateColors(4);

                // Setting the colour properties
                Property colorTopLeft = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(0)).build();
                Property colorBottomLeft = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(2)).build();
                Property colorTopRight = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(1)).build();
                Property colorBottomRight = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(3)).build();

                // Creating the vertices
                Vertex topLeft = Vertex.newBuilder().setX(x).setY(y).addProperties(colorTopLeft).build();
                Vertex bottomLeft = Vertex.newBuilder().setX(x).setY(y + square_size).addProperties(colorBottomLeft).build();
                Vertex topRight = Vertex.newBuilder().setX(x + square_size).setY(y).addProperties(colorTopRight).build();
                Vertex bottomRight = Vertex.newBuilder().setX(x + square_size).setY(y + square_size).addProperties(colorBottomRight).build();

                vertices.add(topLeft);
                vertices.add(topRight);
                vertices.add(bottomLeft);
                vertices.add(bottomRight);

                topVertex.add(topRight);
                topColors.add(colorTopRight);
                bottomVertex.add(bottomRight);
                bottomColors.add(colorBottomRight);

                // Establishing segments
                Segment topLeftToTopRight = createSegment(topLeft, topRight, averageColor(colorTopLeft.getValue(), colorTopRight.getValue()));
                Segment topLeftToBottomLeft = createSegment(topLeft, bottomLeft, averageColor(colorTopLeft.getValue(), colorBottomLeft.getValue()));
                Segment bottomRightToBottomLeft = createSegment(bottomRight, bottomLeft, averageColor(colorBottomRight.getValue(), colorBottomLeft.getValue()));
                Segment bottomRightToTopRight = createSegment(bottomRight, topRight, averageColor(colorBottomRight.getValue(), colorTopRight.getValue()));
                segments.add(topLeftToTopRight);
                segments.add(topLeftToBottomLeft);
                segments.add(bottomRightToBottomLeft);
                segments.add(bottomRightToTopRight);

                if (y > 0) { // vertical segments
                    for (int k = square_size; k < height; k += square_size * 2) {
                        Segment leftSegment = createSegment(previousVertex.get(0), topRight, averageColor(previousColors.get(0).getValue(), colorTopRight.getValue()));
                        Segment rightSegment = createSegment(previousVertex.get(1), topLeft, averageColor(previousColors.get(1).getValue(), colorTopLeft.getValue()));
                        segments.add(leftSegment);
                        segments.add(rightSegment);
                    }

                }

                if (x > 0) { // horizontal segments
                    Segment top = createSegment(topVertex.get(i), topLeft, averageColor(topColors.get(i).getValue(), colorTopLeft.getValue()));
                    Segment bottom = createSegment(bottomVertex.get(i), bottomLeft, averageColor(bottomColors.get(i).getValue(), colorBottomLeft.getValue()));
                    segments.add(top);
                    segments.add(bottom);
                    i++;

                }
                previousVertex.clear();
                previousVertex.add(bottomRight);
                previousVertex.add(bottomLeft);

                previousColors.clear();
                previousColors.add(colorBottomRight);
                previousColors.add(colorBottomLeft);
            }
        }
        return vertices;
    }

    private Set<Vertex> createColorVertices() {
        Set<Vertex> vertices = new LinkedHashSet<>();

        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                ArrayList<String> colorCodes = generateColors(1);
                Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(0)).build();

                Vertex vertex = Vertex.newBuilder().setX(x).setY(y).addProperties(color).build();
                vertices.add(vertex);
            }
        }
        return vertices;
    }

    private Set<Segment> createColorSegments(Set<Vertex> verticesSet) {
        Vertex[] vertices = verticesSet.toArray(new Vertex[verticesSet.size()]);
        Vertex[][] vertices2D = new Vertex[height/square_size][width/square_size];

//        for (Vertex vertex : vertices) {
//            System.out.println(vertex.getX() + ", " + vertex.getY());
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

                    segments.put(counter, new Segment(currVertex, nextVertex));
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

    private Map<Long, Vertex> initializeSquareVerticies(Map<Long, List<Long>> coords) {
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
                    vertices.put(pos, new Vertex(xy.get(0),xy.get(1), new Color(counter%2 == 0 ? 255 : 0,0,0)));
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
