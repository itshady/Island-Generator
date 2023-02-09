package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon {

    private List<Segment> segmentList;
    private Integer id;
    private Structs.Polygon polygon;
    private float thickness = (float) 2;
    private Color color;

    public Polygon(List<Segment> segments) {
        this.color = averageColor(segments);
        segmentList = segments;
    }

    public Polygon(List<Segment> segments, Color color) {
        this.color = color;
        segmentList = segments;

    }

    public Polygon(List<Segment> segments, Float thickness) {
        this.color = averageColor(segments);
        this.thickness = thickness;
        segmentList = segments;
    }

    public Polygon(List<Segment> segments, Color color, Float thickness) {
        this.color = color;
        this.thickness = thickness;
        segmentList = segments;

    }

    public void generatePolygon() {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < segmentList.size(); i++) {
            idList.add(segmentList.get(i).getId());
        }
        polygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(idList).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
    }

    public Structs.Polygon getPolygon() {
        return polygon;
    }


    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        Structs.Property colorProperty = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorStr).build();
        return colorProperty;
    }

    private Structs.Property setThicknessProperty(Float thickness) {
        String polygonThickness = Float.toString(thickness);
        Structs.Property thicknessProperty = Structs.Property.newBuilder().setKey("polygon_thickness").setValue(polygonThickness).build();
        return thicknessProperty;
    }

    private Color averageColor(List<Segment> segments) {
        int totalColors = segments.size();
        int red = 0;
        int blue = 0;
        int green = 0;
        int alpha = 0;
        List<Color> segmentColors = new ArrayList<>();

        for (Segment s : segments) {
            segmentColors.add(s.getColor());
        }

        for (Color color : segmentColors) {

            red += color.getRed();
            blue += color.getBlue();
            green += color.getGreen();
            alpha += color.getAlpha();
        }

        red = red/totalColors;
        blue = blue/totalColors;
        green = green/totalColors;
        alpha = alpha/totalColors;
        return new Color(red, blue, green, alpha);
    }

}
