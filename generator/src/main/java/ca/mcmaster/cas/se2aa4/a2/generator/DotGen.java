package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
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
        // map out points and store id in segment
        // depth first search
        Set<ca.mcmaster.cas.se2aa4.a2.generator.Vertex> vertices = createColorVertices();
//        Set<Segment> segments = createColorSegments(vertices);
        Set<Vertex> toRudimentaryVertex = extractLameVertices(vertices);
        return Mesh.newBuilder().addAllVertices(toRudimentaryVertex).build();
    }


    private Set<ca.mcmaster.cas.se2aa4.a2.generator.Vertex> createColorVertices() {
        Set<ca.mcmaster.cas.se2aa4.a2.generator.Vertex> vertices = new LinkedHashSet<>();

        // Create all the vertices
        for(int x = 0; x < width; x += square_size) {
            for(int y = 0; y < height; y += square_size) {
                vertices.add(new ca.mcmaster.cas.se2aa4.a2.generator.Vertex(x,y, new Color(0,0,0)));
            }
        }
        return vertices;
    }

    private Set<Vertex> extractLameVertices(Set<ca.mcmaster.cas.se2aa4.a2.generator.Vertex> vertices) {
        Set<Vertex> lameSet = new LinkedHashSet<>();
        for (ca.mcmaster.cas.se2aa4.a2.generator.Vertex vertex : vertices) {
            lameSet.add(vertex.getVertex());
        }
        return lameSet;
    }
}
