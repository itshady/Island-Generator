package Geometries;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

/**
 * Mutable Segment class which is composed of two unique vertices
 */
public class Segment {
    private Structs.Segment segment;
    private float thickness = (float) 0.5;
    private final Color color;
    private Vertex v1;
    private Vertex v2;
    private Integer id;
    private final PropertyHandler propertyHandler = new PropertyHandler();

    public Vertex getV1() {
        return v1;
    }

    public Vertex getV2() {
        return v2;
    }

    public void setId(int value) {
        id = value;
    }

    public Integer getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public Segment(Vertex v1, Vertex v2) {
        this.color = propertyHandler.averageColor(Arrays.asList(v1.getColor(), v2.getColor()));
        setSegment(v1, v2);
    }

    public Segment(Vertex v1, Vertex v2, Color color) {
        this.color = color;
        setSegment(v1, v2);
    }

    public Segment(Vertex v1, Vertex v2, Float thickness) {
        this.thickness = thickness;
        this.color = propertyHandler.averageColor(Arrays.asList(v1.getColor(), v2.getColor()));
        setSegment(v1, v2);
    }

    public Segment(Vertex v1, Vertex v2, Color color, Float thickness) {
        this.color = color;
        this.thickness = thickness;
        setSegment(v1, v2);
    }

    public void generateSegment() {
        segment = Structs.Segment.newBuilder().setV1Idx(v1.getId()).setV2Idx(v2.getId())
                .addProperties(propertyHandler.setColorProperty(color))
                .addProperties(propertyHandler.setThicknessProperty(thickness))
                .build();
    }

    public Structs.Segment getSegment() {
        return segment;
    }

    private void setSegment(Vertex vertex1, Vertex vertex2) {
        v1 = vertex1;
        v2 = vertex2;
    }

    /**
     * Segments are equivalent if their two vertices are equal, in any order.
     * @param o: Object o to compare to
     * @return Boolean: True if they are equal, false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return (Objects.equals(v1, segment.v1) && Objects.equals(v2, segment.v2)) ||
                (Objects.equals(v1, segment.v2) && Objects.equals(v2, segment.v1));
    }

    /**
     * Hashes the segments based on their vertices.
     * Vertices should never be the same, so this is a good way to keep the order, so "equivalent" segments hash to the same hash
     * @return hashed integer
     */
    @Override
    public int hashCode() {
        if (v1.getX() < v2.getX()) return Objects.hash(v1,v2);
        if (v1.getX() > v2.getX()) return Objects.hash(v2,v1);
        if (v1.getY() < v2.getY()) return Objects.hash(v1,v2);
        if (v1.getY() > v2.getY()) return Objects.hash(v2,v1);
        return Objects.hash(v1,v2);
    }
}
