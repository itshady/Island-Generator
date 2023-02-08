package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.Random;

public class Segment {
    private Structs.Segment segment;
    private float thickness = (float) 0.5;
    private Color color;
    private Vertex v1;
    private Vertex v2;
    private final double precision = 0.01;
    private int id;

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
        setSegment(v1, v2, color);
    }

    public Segment(Vertex v1, Vertex v2, Float thickness) {
        this.thickness = thickness;
        color = averageColor(v1.getColor(), v2.getColor());
        setSegment(v1, v2, color, thickness);
    }

//    public Segment(Long x1, Long y1, Long x2, Long y2) {
//        color = generateColors();
//        setSegment(x1, y1, x2, y2, color);
//    }
//
//    public Segment(Long x1, Long y1, Long x2, Long y2, Float thickness) {
//        color = generateColors();
//        setSegment(x1, y1, x2, y2, color, thickness);
//    }

    public Segment(Vertex v1, Vertex v2, Color color) {
        this.color = color;
        setSegment(v1, v2, color);
    }

    public Segment(Vertex v1, Vertex v2, Color color, Float thickness) {
        this.color = color;
        this.thickness = thickness;
        setSegment(v1, v2, color, thickness);
    }

//    public Segment(Long x1, Long y1, Long x2, Long y2, Color color) {
//        this.color = color;
//        setSegment(x1, y1, x2, y2, color);
//    }
//
//    public Segment(Long x1, Long y1, Long x2, Long y2, Color color, Float thickness) {
//        this.color = color;
//        setSegment(x1, y1, x2, y2, color, thickness);
//    }

    public void generateSegment() {
        segment = Structs.Segment.newBuilder().setV1Idx(v1.getId()).setV2Idx(v2.getId()).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
    }

    public Structs.Segment getSegment() {
        return segment;
    }

    private void setSegment(Vertex vertex1, Vertex vertex2, Color color) {
//        Structs.Property v1 = Structs.Property.newBuilder().setKey("vertex1").setValue((vertex1.getX())+","+(vertex1.getY())).build();
//        Structs.Property v2 = Structs.Property.newBuilder().setKey("vertex2").setValue((vertex2.getX())+","+(vertex2.getY())).build();
//        segment = Structs.Segment.newBuilder().addProperties(v1).addProperties(v2).addProperties(setColorProperty(color)).build();
//        segment = Structs.Segment.newBuilder().setV1Idx(vertex1.getId()).setV2Idx(vertex2.getId()).addProperties(setColorProperty(color)).build();
        v1 = vertex1;
        v2 = vertex2;
    }

    private void setSegment(Vertex vertex1, Vertex vertex2, Color color, Float thickness) {
//        Structs.Property v1 = Structs.Property.newBuilder().setKey("vertex1").setValue((vertex1.getX())+","+(vertex1.getY())).build();
//        Structs.Property v2 = Structs.Property.newBuilder().setKey("vertex2").setValue((vertex2.getX())+","+(vertex2.getY())).build();
//        segment = Structs.Segment.newBuilder().addProperties(v1).addProperties(v2).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
//        segment = Structs.Segment.newBuilder().setV1Idx(vertex1.getId()).setV2Idx(vertex2.getId()).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
        v1 = vertex1;
        v2 = vertex2;
    }

//    private void setSegment(Long x1, Long y1, Long x2, Long y2, Color color) {
//        Structs.Property v1 = Structs.Property.newBuilder().setKey("vertex1").setValue((x1)+","+(y1)).build();
//        Structs.Property v2 = Structs.Property.newBuilder().setKey("vertex2").setValue((x2)+","+(y2)).build();
//        segment = Structs.Segment.newBuilder().addProperties(v1).addProperties(v2).addProperties(setColorProperty(color)).build();
//    }

//    private void setSegment(Long x1, Long y1, Long x2, Long y2, Color color, Float thickness) {
//        Structs.Property v1 = Structs.Property.newBuilder().setKey("vertex1").setValue((x1)+","+(y1)).build();
//        Structs.Property v2 = Structs.Property.newBuilder().setKey("vertex2").setValue((x2)+","+(y2)).build();
//        segment = Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx().addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
//    }

    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        Structs.Property colorProperty = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorStr).build();
        return colorProperty;
    }

    private Structs.Property setThicknessProperty(Float thickness) {
        String segmentThickness = Float.toString(thickness);
        Structs.Property thicknessProperty = Structs.Property.newBuilder().setKey("segment_thickness").setValue(segmentThickness).build();
        return thicknessProperty;
    }

    private Color averageColor(Color color1, Color color2) {
        int red = (color1.getRed()+color2.getRed())/2;
        int blue = (color1.getBlue()+color2.getBlue())/2;
        int green = (color1.getGreen()+color2.getGreen())/2;
        int alpha = (color1.getAlpha()+color2.getAlpha())/2;
        return new Color(red, blue, green, alpha);
    }

    private Color generateColors() {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        int alpha = bag.nextInt(1,255);
        return new Color(red, green, blue, alpha);
    }
}
