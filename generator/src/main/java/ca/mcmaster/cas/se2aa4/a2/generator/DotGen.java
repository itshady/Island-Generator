package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 500;
    private final int height = 500;
    private final int square_size = 20;

    public Mesh generate() {
        Set<Segment> segments = new HashSet<>();//createSegments(vertices);
        Set<Vertex> vertices = createEmptyVertices(segments);

        // Distribute colors randomly. Vertices are immutable, need to enrich them
//        Set<Vertex> verticesWithColors = new LinkedHashSet<>();
//        Random bag = new Random();
//        int counter = 0;
//        for(Vertex v: vertices){
//            int red = 0;// bag.nextInt(255);
//            int green = 0;// bag.nextInt(255);
//            int blue = 0;// bag.nextInt(255);
//            String colorCode = red + "," + green + "," + blue;
//            if (counter < 74) {
//                colorCode = 11+","+178+","+11;
//                System.out.println("HERE "+counter);
//            }
//            counter++;
//            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
//            // Property test = Property.newBuilder().setKey("hehe").setValue("123").build();
//            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
//            verticesWithColors.add(colored);
//            System.out.println(colored.getPropertiesList());
//        }
//        System.out.println(verticesWithColors);
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

        System.out.println("color1: "+color1Int+" - color2: "+color2Int);
        Integer averageRed = average(color1Int.get(0),color2Int.get(0));
        Integer averageGreen = average(color1Int.get(1),color2Int.get(1));
        Integer averageBlue = average(color1Int.get(2),color2Int.get(2));

        String avgColor = ""+averageRed+","+averageGreen+","+averageBlue;
        return avgColor;
    }

    private Set<Vertex> createEmptyVertices(Set<Segment> segments) {
        Set<Vertex> vertices = new LinkedHashSet<>();

        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                ArrayList<String> colorCodes = generateColors(4);

                Property colorTopLeft = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(0)).build();
                Property colorTopRight = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(1)).build();
                Property colorBottomLeft = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(2)).build();
                Property colorBottomRight = Property.newBuilder().setKey("rgb_color").setValue(colorCodes.get(3)).build();

                Vertex topLeft = Vertex.newBuilder().setX(x).setY(y).addProperties(colorTopLeft).build();
                Vertex topRight = Vertex.newBuilder().setX(x+square_size).setY(y).addProperties(colorTopRight).build();
                Vertex bottomLeft = Vertex.newBuilder().setX(x).setY(y+square_size).addProperties(colorBottomLeft).build();
                Vertex bottomRight = Vertex.newBuilder().setX(x+square_size).setY(y+square_size).addProperties(colorBottomRight).build();
                vertices.add(topLeft);
                vertices.add(topRight);
                vertices.add(bottomLeft);
                vertices.add(bottomRight);

                Segment topLeftToTopRight = createSegment(topLeft, topRight, averageColor(colorTopLeft.getValue(), colorTopRight.getValue()));
                Segment topLeftToBottomLeft = createSegment(topLeft, bottomLeft, averageColor(colorTopLeft.getValue(), colorBottomLeft.getValue()));
                Segment bottomRightToBottomLeft = createSegment(bottomRight, bottomLeft, averageColor(colorBottomRight.getValue(), colorBottomLeft.getValue()));
                Segment bottomRightToTopRight = createSegment(bottomRight, topRight, averageColor(colorBottomRight.getValue(), colorTopRight.getValue()));
                segments.add(topLeftToTopRight);
                segments.add(topLeftToBottomLeft);
                segments.add(bottomRightToBottomLeft);
                segments.add(bottomRightToTopRight);
            }
        }
        return vertices;
    }

}
