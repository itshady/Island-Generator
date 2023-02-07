package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        List<Vertex> vertexList = aMesh.getVerticesList();
        List<Segment> segmentsList = aMesh.getSegmentsList();
        List<Structs.Polygon> polygonList = aMesh.getPolygonsList();

        for (Structs.Polygon p: polygonList) {
            System.out.println("Segments: " + p.getSegmentIdxsList());
            System.out.println("("+vertexList.get(segmentsList.get(p.getSegmentIdxs(0)).getV1Idx()).getY()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(1)).getV1Idx()).getY()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(2)).getV1Idx()).getY()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(3)).getV1Idx()).getY()+")");
            System.out.println("("+vertexList.get(segmentsList.get(p.getSegmentIdxs(0)).getV1Idx()).getX()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(1)).getV1Idx()).getX()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(2)).getV1Idx()).getX()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(3)).getV1Idx()).getX()+")");

            System.out.println("("+vertexList.get(segmentsList.get(p.getSegmentIdxs(0)).getV2Idx()).getY()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(1)).getV2Idx()).getY()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(2)).getV2Idx()).getY()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(3)).getV2Idx()).getY()+")");
            System.out.println("("+vertexList.get(segmentsList.get(p.getSegmentIdxs(0)).getV2Idx()).getX()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(1)).getV2Idx()).getX()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(2)).getV2Idx()).getX()+", "+vertexList.get(segmentsList.get(p.getSegmentIdxs(3)).getV2Idx()).getX()+")");
        }



        // Visualizing Vertices
        for (Vertex v: aMesh.getVerticesList()) {
            float vertexThickness = extractVertexThickness(v.getPropertiesList());
            double centre_x = v.getX() - (vertexThickness/2.0d);
            double centre_y = v.getY() - (vertexThickness/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, vertexThickness, vertexThickness);
            canvas.fill(point);
            canvas.setColor(old);
        }

        // Visualizing Segments
        for (Segment s: aMesh.getSegmentsList()) {
            Stroke segmentStroke = new BasicStroke(extractSegmentThickness(s.getPropertiesList()));
            canvas.setStroke(segmentStroke);
            Vertex v1 = vertexList.get(s.getV1Idx());
            Vertex v2 = vertexList.get(s.getV2Idx());
            Point2D point1 = new Point2D.Double(v1.getX(), v1.getY());
            Point2D point2 = new Point2D.Double(v2.getX(), v2.getY());
            Color old = canvas.getColor();
            canvas.setColor(extractColor(s.getPropertiesList()));
            Line2D line = new Line2D.Double(point1, point2);
            canvas.draw(line);
            canvas.setColor(old);
        }

        // Visualizing Polygons
        for (Structs.Polygon p: aMesh.getPolygonsList()) {
            Color old = canvas.getColor();
            canvas.setColor(Color.BLUE);
            List<Integer> polygonSegments = p.getSegmentIdxsList();
            int[] xValues = new int[polygonSegments.size()];
            int[] yValues = new int[polygonSegments.size()];
            for (int i = 0; i < polygonSegments.size(); i++) {
                if (i > 0) {
                    Segment curr = segmentsList.get(polygonSegments.get(i));
                    Segment prev = segmentsList.get(polygonSegments.get(i-1));
                    if (curr.getV1Idx() != prev.getV1Idx()) {
                        xValues[i] = (int) vertexList.get(curr.getV2Idx()).getX();
                        yValues[i] = (int) vertexList.get(curr.getV2Idx()).getY();
                    } else {
                        xValues[i] = (int) vertexList.get(curr.getV1Idx()).getX();
                        yValues[i] = (int) vertexList.get(curr.getV1Idx()).getY();
                    }
                } else {
                    Segment segment = segmentsList.get(polygonSegments.get(i));
                    xValues[i] = (int) vertexList.get(segment.getV1Idx()).getX();
                    yValues[i] = (int) vertexList.get(segment.getV1Idx()).getY();
//                    Segment next = segmentsList.get(polygonSegments.get(i+1));
//                    if (segment.getV1Idx() == next.getV1Idx()) {
//                        xValues[i] = (int) vertexList.get(segment.getV1Idx()).getX();
//                        yValues[i] = (int) vertexList.get(segment.getV1Idx()).getY();
//                    } else {
//                        xValues[i] = (int) vertexList.get(segment.getV2Idx()).getX();
//                        yValues[i] = (int) vertexList.get(segment.getV2Idx()).getY();
//                    }
                }
            }
            System.out.println(Arrays.toString(xValues));
            System.out.println(Arrays.toString(yValues));
//            xValues[polygonSegments.size()] = (int) vertexList.get(segmentsList.get(4).getV2Idx()).getX();
//            yValues[polygonSegments.size()] = (int) vertexList.get(segmentsList.get(4).getV2Idx()).getY();
            Polygon polygon = new Polygon(xValues, yValues, polygonSegments.size());
            canvas.fillPolygon(polygon);
            canvas.setColor(old);
        }
    }

    private Point2D extractV1(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("vertex1")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return null;
        String[] raw = val.split(",");  // String: [5,10]
        // ["5","10"]
        Double x = Double.parseDouble(raw[0]);
        Double y = Double.parseDouble(raw[1]);
        return new Point2D.Double(x,y);
    }

    private Point2D extractV2(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("vertex2")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return null;
        String[] raw = val.split(",");  // String: [5,10]
        // ["5","10"]
        Double x = Double.parseDouble(raw[0]);
        Double y = Double.parseDouble(raw[1]);
        return new Point2D.Double(x,y);
    }

    private Color extractColor(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("rgb_color")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return Color.BLACK;
        String[] raw = val.split(",");
        int red = Integer.parseInt(raw[0]);
        int green = Integer.parseInt(raw[1]);
        int blue = Integer.parseInt(raw[2]);
        int alpha = Integer.parseInt(raw[3]);
        return new Color(red, green, blue, alpha);
    }

    private Float extractSegmentThickness(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("segment_thickness")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 0.5f;
        return Float.parseFloat(val);
    }

    private Float extractVertexThickness(List<Property> properties) {
        String val = null;
        for(Property p: properties) {
            if (p.getKey().equals("vertex_thickness")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 3f;
        return Float.parseFloat(val);
    }

}