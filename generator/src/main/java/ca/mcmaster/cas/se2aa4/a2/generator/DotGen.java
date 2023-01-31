package ca.mcmaster.cas.se2aa4.a2.generator;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

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
        Set<Vertex> vertices = new HashSet<>();
        Set<Segment> segments = new HashSet<>();

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
                segments.add(Segment.newBuilder().setV1Idx(topLeft.hashCode()).setV2Idx(topRight.hashCode()).build());
                segments.add(Segment.newBuilder().setV1Idx(topLeft.hashCode()).setV2Idx(bottomLeft.hashCode()).build());
                segments.add(Segment.newBuilder().setV1Idx(bottomLeft.hashCode()).setV2Idx(bottomRight.hashCode()).build());
                segments.add(Segment.newBuilder().setV1Idx(topRight.hashCode()).setV2Idx(bottomRight.hashCode()).build());
            }
        }

        // Distribute colors randomly. Vertices are immutable, need to enrich them
        Set<Vertex> verticesWithColors = new HashSet<>();
        Random bag = new Random();
        for(Vertex v: vertices){
            int red = bag.nextInt(255);
            int green = bag.nextInt(255);
            int blue = bag.nextInt(255);
            String colorCode = red + "," + green + "," + blue;
            Property color = Property.newBuilder().setKey("rgb_color").setValue(colorCode).build();
            Vertex colored = Vertex.newBuilder(v).addProperties(color).build();
            verticesWithColors.add(colored);
        }
        return Mesh.newBuilder().addAllVertices(verticesWithColors).addAllSegments(segments).build();
    }

}
