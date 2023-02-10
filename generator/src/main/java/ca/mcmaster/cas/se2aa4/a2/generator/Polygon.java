package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Polygon {

    private List<Segment> segmentList;
    private List<List<Double>> vertexPointList;
    private Integer id;
    private Structs.Polygon polygon;
    private float thickness = (float) 2;
    private Color color;
    private Centroid centroid;
    private final int width = 520;
    private final int height = 520;
    private final double precision = 0.01;
    private Set<Integer> neighbourIdxSet = new HashSet<>();
    private final int matrixWidth = (int) Math.round(width/precision);
    private final int matrixHeight = (int) Math.round(height/precision);

    public Polygon(Integer id, List<Segment> segments) {
        this.id = id;
        this.color = averageColor(segments);
        segmentList = segments;
    }

    public Polygon(Integer id, List<Segment> segments, Color color, List<List<Double>> points) {
        this.id = id;
        this.color = color;
        segmentList = segments;
        vertexPointList = points;
        centroid = generateCentroid();
    }

    public Polygon(Integer id, List<Segment> segments, Float thickness, List<List<Double>> points) {
        this.id = id;
        this.color = averageColor(segments);
        this.thickness = thickness;
        segmentList = segments;
        vertexPointList = points;
        centroid = generateCentroid();
    }

    public Polygon(Integer id, List<Segment> segments, Color color, Float thickness, List<List<Double>> points) {
        this.id = id;
        this.color = color;
        this.thickness = thickness;
        segmentList = segments;
        vertexPointList = points;
        centroid = generateCentroid();

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

    public void setId(int newId) {
        id = newId;
    }

    public Integer getId() {
        return id;
    }

    private Centroid generateCentroid() {
        List<Double> centroidCoords = calculateCentroid();
        Double x = centroidCoords.get(0);
        Double y = centroidCoords.get(1);
        // When casting the whole line as an int, the value maxes out at Integer.max_value. This way it goes to negative numbers.
        Integer id = ((int) (Math.round(y)))*matrixWidth + (int) Math.round(x);
        return new Centroid(id, x, y, new Color(255,0,0));
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

        centroid.add(cx/precision);
        centroid.add(cy/precision);

        return centroid;
    }

    public Structs.Polygon getPolygon() {
        return polygon;
    }

    public List<Segment> getSegmentList() {
        return segmentList;
    }

    public void addPolygonNeighbourSet (List<Polygon> polygonSet) {
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
