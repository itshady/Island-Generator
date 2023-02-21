package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
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
}
