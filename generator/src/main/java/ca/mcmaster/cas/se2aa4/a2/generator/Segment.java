package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Segment {
    private Structs.Segment segment;
    private float thickness = (float) 0.5;
    private final Color color;
    private Vertex v1;
    private Vertex v2;
    private Integer id;
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
        color = averageColor(v1.getColor(), v2.getColor());
        setSegment(v1, v2);
    }

    public Segment(Vertex v1, Vertex v2, Color color) {
        this.color = color;
        setSegment(v1, v2);
    }

    public Segment(Vertex v1, Vertex v2, Float thickness) {
        this.thickness = thickness;
        this.color = averageColor(v1.getColor(), v2.getColor());;
        setSegment(v1, v2);
    }

    public Segment(Vertex v1, Vertex v2, Color color, Float thickness) {
        this.color = color;
        this.thickness = thickness;
        setSegment(v1, v2);
    }

    public void generateSegment() {
        segment = Structs.Segment.newBuilder().setV1Idx(v1.getId()).setV2Idx(v2.getId()).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
    }

    public Structs.Segment getSegment() {
        return segment;
    }

    private void setSegment(Vertex vertex1, Vertex vertex2) {
        v1 = vertex1;
        v2 = vertex2;
    }

    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        return Structs.Property.newBuilder().setKey("rgba_color").setValue(colorStr).build();
    }

    private Structs.Property setThicknessProperty(Float thickness) {
        String segmentThickness = Float.toString(thickness);
        return Structs.Property.newBuilder().setKey("thickness").setValue(segmentThickness).build();
    }

    private Color averageColor(Color color1, Color color2) {
        int red = (color1.getRed()+color2.getRed())/2;
        int blue = (color1.getBlue()+color2.getBlue())/2;
        int green = (color1.getGreen()+color2.getGreen())/2;
        int alpha = (color1.getAlpha()+color2.getAlpha())/2;
        return new Color(red, green, blue, alpha);
    }

    // to be equivalent means to have the same 2 vertices, in any order
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Segment segment = (Segment) o;
        return (Objects.equals(v1, segment.v1) && Objects.equals(v2, segment.v2)) ||
                (Objects.equals(v1, segment.v2) && Objects.equals(v2, segment.v1));
    }

    // vertices should never be the same, so this is a good way to keep the order, so "equivalent" segments hash to the same hash
    @Override
    public int hashCode() {
        if (v1.getX() < v2.getX()) return Objects.hash(v1,v2);
        if (v1.getX() > v2.getX()) return Objects.hash(v2,v1);
        if (v1.getY() < v2.getY()) return Objects.hash(v1,v2);
        if (v1.getY() > v2.getY()) return Objects.hash(v2,v1);
        return Objects.hash(v1,v2);
    }
}
