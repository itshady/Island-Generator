package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Polygon {

    private List<Segment> segmentList;
    private List<List<Double>> vertexPointList;
    private Integer id;
    private Structs.Polygon polygon;
    private float thickness = (float) 2;
    private Color color;

    public Polygon(List<Segment> segments) {
        this.color = averageColor(segments);
        segmentList = segments;
    }

    public Polygon(List<Segment> segments, Color color, List<List<Double>> points) {
        this.color = color;
        segmentList = segments;
        vertexPointList = points;
    }

    public Polygon(List<Segment> segments, Float thickness, List<List<Double>> points) {
        this.color = averageColor(segments);
        this.thickness = thickness;
        segmentList = segments;
        vertexPointList = points;
    }

    public Polygon(List<Segment> segments, Color color, Float thickness, List<List<Double>> points) {
        this.color = color;
        this.thickness = thickness;
        segmentList = segments;
        vertexPointList = points;
    }

    public void generatePolygon() {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < segmentList.size(); i++) {
            idList.add(segmentList.get(i).getId());
        }
        List<Double> centroid = calculateCentroid();
        System.out.println("Segments: " + vertexPointList + " Centroid:" + centroid);
        polygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(idList).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
    }

    private List<Double> calculateCentroid() {
        List<Double> centroid = new ArrayList<>();
        double cx = 0, cy = 0;
        double area = 0;
        int numPoints = vertexPointList.size();

        for (int i = 0; i < numPoints - 1; i++) {
            double x1 = vertexPointList.get(i).get(0); // x value
            double y1 = vertexPointList.get(i).get(1); // y value
            double x2 = vertexPointList.get(i+1).get(0);
            double y2 = vertexPointList.get(i+1).get(1);
            double aTemp = x1 * y2 - x2 * y1;
            cx += (x1 + x2) * aTemp;
            cy += (y1 + y2) * aTemp;
            area += aTemp;
        }

        // Complete logic for the last point
        double x1 = vertexPointList.get(numPoints - 1).get(0);
        double y1 = vertexPointList.get(numPoints - 1).get(1);
        double x2 = vertexPointList.get(0).get(0);
        double y2 = vertexPointList.get(0).get(1);
        double aTemp = x1 * y2 - x2 * y1;
        cx += (x1 + x2) * aTemp;
        cy += (y1 + y2) * aTemp;
        area += aTemp;

        area /= 2;
        cx /= (6 * area);
        cy /= (6 * area);

        centroid.add(cx);
        centroid.add(cy);

        return centroid;
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
