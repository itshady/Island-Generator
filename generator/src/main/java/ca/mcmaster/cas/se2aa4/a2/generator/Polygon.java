package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Polygon {

    private final List<Segment> segmentList;
    private final Integer id;
    private Structs.Polygon polygon;
    private float thickness = (float) 2;
    private final Color color;
    private Centroid centroid;
    private final Set<Integer> neighbourIdxSet = new HashSet<>();

    public Polygon(Integer id, List<Segment> segments) {
        this.id = id;
        this.color = averageColor(segments);
        segmentList = segments;
    }

    public Polygon(Integer id, List<Segment> segments, Color color) {
        this.id = id;
        this.color = color;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
    }

    public Polygon(Integer id, List<Segment> segments, Float thickness) {
        this.id = id;
        this.color = averageColor(segments);
        this.thickness = thickness;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
    }

    public Polygon(Integer id, List<Segment> segments, Color color, Float thickness) {
        this.id = id;
        this.color = color;
        this.thickness = thickness;
        segmentList = segments;
        centroid = new Centroid(0.0,0.0);
    }

    public void generatePolygon() {
        List<Integer> idList = new ArrayList<>();
        for (int i = 0; i < segmentList.size(); i++) {
            idList.add(segmentList.get(i).getId());
        }

        //System.out.println("Segments: " + vertexPointList + " Centroid:" + centroid.getVertex());
        polygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(idList).setCentroidIdx(centroid.getId()).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).addAllNeighborIdxs(neighbourIdxSet).build();
    }

    public Integer getCentroidId() {
        return centroid.getId();
    }

    public Centroid getCentroid() {
        return centroid;
    }

    public Integer getId() {
        return id;
    }

    public void setCentroid(Centroid centroid) {
        this.centroid = centroid;
    }

    public Structs.Polygon getPolygon() {
        return polygon;
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void addPolygonNeighbourSet (Set<Polygon> polygonSet) {
        for (Polygon p : polygonSet) {
            this.neighbourIdxSet.add(p.getId());
        }
    }

    public void removePolygonNeighbour (Polygon neighbour) {
        this.neighbourIdxSet.remove(neighbour.getId());
    }

    public Set<Integer> getPolygonNeighbours () {
        return this.neighbourIdxSet;
    }

    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        Structs.Property colorProperty = Structs.Property.newBuilder().setKey("rgba_color").setValue(colorStr).build();
        return colorProperty;
    }

    private Structs.Property setThicknessProperty(Float thickness) {
        String polygonThickness = Float.toString(thickness);
        Structs.Property thicknessProperty = Structs.Property.newBuilder().setKey("thickness").setValue(polygonThickness).build();
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
