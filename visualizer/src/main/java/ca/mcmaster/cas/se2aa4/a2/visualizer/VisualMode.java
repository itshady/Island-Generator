package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public abstract class VisualMode {

    protected void render(Structs.Mesh aMesh, Graphics2D canvas) {
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        List<Structs.Vertex> vertexList = aMesh.getVerticesList();
        List<Structs.Segment> segmentsList = aMesh.getSegmentsList();

        // Visualizing polygons
        visualizePolygon(isDebug(), aMesh, canvas, vertexList, segmentsList);

        // Visualizing Segments
        visualizeSegments(isDebug(), aMesh, canvas, vertexList);

        // Visualizing Vertices and Centroids
        visualizeVertices(aMesh, canvas);
    }

    protected abstract void visualizeVertices(Structs.Mesh aMesh, Graphics2D canvas);

    protected abstract boolean isDebug();

    protected void visualizeSegments (boolean debug, Structs.Mesh aMesh, Graphics2D canvas, List<Structs.Vertex> vertexList) {
        for (Structs.Segment s: aMesh.getSegmentsList()) {
            Stroke segmentStroke = new BasicStroke(extractThickness(s.getPropertiesList()));
            canvas.setStroke(segmentStroke);
            Structs.Vertex v1 = vertexList.get(s.getV1Idx());
            Structs.Vertex v2 = vertexList.get(s.getV2Idx());
            Point2D point1 = new Point2D.Double(v1.getX(), v1.getY());
            Point2D point2 = new Point2D.Double(v2.getX(), v2.getY());
            Color old = canvas.getColor();
            Color color = debug ? Color.BLACK : extractColor(s.getPropertiesList());
            canvas.setColor(color);
            Line2D line = new Line2D.Double(point1, point2);
            canvas.draw(line);
            canvas.setColor(old);
        }
    }

    protected void visualizePolygon(boolean debug, Structs.Mesh aMesh, Graphics2D canvas, List<Structs.Vertex> vertexList, List<Structs.Segment> segmentsList) {
        for (Structs.Polygon p: aMesh.getPolygonsList()) {
            List<Integer> polygonSegments = p.getSegmentIdxsList();
            Color old = canvas.getColor();
            Stroke polygonStroke = new BasicStroke(extractThickness(p.getPropertiesList()));
            canvas.setStroke(polygonStroke);
            Color color = debug ? Color.BLACK : extractColor(p.getPropertiesList());
            canvas.setColor(color);
            int[] xValues = new int[polygonSegments.size()];
            int[] yValues = new int[polygonSegments.size()];
            updateCoordsForPolygons(vertexList, segmentsList, polygonSegments, xValues, yValues);
            Polygon polygon = new Polygon(xValues, yValues, polygonSegments.size());
            if (debug) canvas.drawPolygon(polygon);
            else canvas.fillPolygon(polygon);
            canvas.setColor(old);
        }
    }

    protected void updateCoordsForPolygons(List<Structs.Vertex> vertexList, List<Structs.Segment> segmentsList, List<Integer> polygonSegments, int[] xValues, int[] yValues) {
        for (int i = 0; i < polygonSegments.size(); i++) {
            if (i > 0) {
                Structs.Segment curr = segmentsList.get(polygonSegments.get(i));
                Structs.Segment prev = segmentsList.get(polygonSegments.get(i-1));
                if (curr.getV1Idx() == prev.getV1Idx() || curr.getV1Idx() == prev.getV2Idx()) {
                    xValues[i] = (int) vertexList.get(curr.getV1Idx()).getX();
                    yValues[i] = (int) vertexList.get(curr.getV1Idx()).getY();
                } else {
                    xValues[i] = (int) vertexList.get(curr.getV2Idx()).getX();
                    yValues[i] = (int) vertexList.get(curr.getV2Idx()).getY();
                }
            } else {
                Structs.Segment segment = segmentsList.get(polygonSegments.get(i));
                Structs.Segment next = segmentsList.get(polygonSegments.get(i+1));
                if (segment.getV1Idx() != next.getV1Idx() && segment.getV1Idx() != next.getV2Idx()) {
                    xValues[i] = (int) vertexList.get(segment.getV1Idx()).getX();
                    yValues[i] = (int) vertexList.get(segment.getV1Idx()).getY();
                } else {
                    xValues[i] = (int) vertexList.get(segment.getV2Idx()).getX();
                    yValues[i] = (int) vertexList.get(segment.getV2Idx()).getY();
                }
            }
        }
    }

    protected Color extractColor(java.util.List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("rgba_color")) {
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

    protected boolean isCentroid(java.util.List<Structs.Property> properties) {
        String val = "false";
        for(Structs.Property p: properties) {
            if (p.getKey().equals("is_centroid")) {
                val = p.getValue();
            }
        }
        return val.equals("true");
    }

    protected Float extractThickness(List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("thickness")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return 2f;
        return Float.parseFloat(val);
    }
}
