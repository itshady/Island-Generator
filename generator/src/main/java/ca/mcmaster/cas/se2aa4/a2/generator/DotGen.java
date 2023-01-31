package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Random;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

public class DotGen {

    private final int width = 500-20;
    private final int height = 500-20;
    private final int square_size = 20;

    public Mesh generate() {
        Set<Segment> segments = new HashSet<>();//createSegments(vertices);
        Set<Vertex> vertices = createEmptyVertices(segments);

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        Set<Vertex> verticesWithColors = new LinkedHashSet<>();
        Random bag = new Random();
        int counter = 0;
        for(Vertex v: vertices){
            int red = 0;// bag.nextInt(255);
            int green = 0;// bag.nextInt(255);
            int blue = 0;// bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            if (counter < 74) {
                colorCode = 11+","+178+","+11;
                System.out.println("HERE "+counter);
            }
            counter++;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            // Property test = Property.newBuilder().setKey("hehe").setValue("123").build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
            System.out.println(colored.getPropertiesList());
        }
        System.out.println(verticesWithColors);
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

    private Segment createSegment(Vertex vertex1, Vertex vertex2) {
        Property v1 = Property.newBuilder().setKey("vertex1").setValue("["+vertex1.getX()+","+vertex1.getY()+"]").build();
        Property v2 = Property.newBuilder().setKey("vertex2").setValue("["+vertex2.getX()+","+vertex2.getY()+"]").build();
        return Segment.newBuilder().addProperties(v1).addProperties(v2).build();
    }

    private Set<Vertex> createEmptyVertices(Set<Segment> segments) {
        Set<Vertex> vertices = new LinkedHashSet<>();

        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                Vertex topLeft = Vertex.newBuilder().setX((double) x).setY((double) y).build();
                Vertex topRight = Vertex.newBuilder().setX((double) x+square_size).setY((double) y).build();
                Vertex bottomLeft = Vertex.newBuilder().setX((double) x).setY((double) y+square_size).build();
                Vertex bottomRight = Vertex.newBuilder().setX((double) x+square_size).setY((double) y+square_size).build();
                vertices.add(topLeft);
                vertices.add(topRight);
                vertices.add(bottomLeft);
                vertices.add(bottomRight);

                Segment topLeftToTopRight = createSegment(topLeft, topRight);
                Segment topLeftToBottomLeft = createSegment(topLeft, bottomLeft);
                Segment bottomRightToBottomLeft = createSegment(bottomRight, bottomLeft);
                Segment bottomRightToTopRight = createSegment(bottomRight, topRight);
                segments.add(topLeftToTopRight);
                segments.add(topLeftToBottomLeft);
                segments.add(bottomRightToBottomLeft);
                segments.add(bottomRightToTopRight);
            }
        }
        return vertices;
    }

}
