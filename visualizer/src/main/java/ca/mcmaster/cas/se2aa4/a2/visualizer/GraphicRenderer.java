package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class GraphicRenderer {

    private static final int THICKNESS = 3;
    public void render(Mesh aMesh, Graphics2D canvas) {
        canvas.setColor(Color.BLACK);
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        for (Segment segment : aMesh.getSegmentsList()) {
            Stroke segmentStroke = new BasicStroke(extractSegmentThickness(segment.getPropertiesList()));
            canvas.setStroke(segmentStroke);
            System.out.println(segment);
            System.out.println("NEXT");
            Color old = canvas.getColor();
            canvas.setColor(extractColor(segment.getPropertiesList()));
            Line2D line = new Line2D.Double(extractV1(segment.getPropertiesList()), extractV2(segment.getPropertiesList()));
            canvas.draw(line);
            canvas.setColor(old);
        }

        Color old2 = canvas.getColor();
        canvas.setColor(Color.BLUE);
        int[] xPoints = {0, 0, 20, 20, 0};
        int[] yPoints = {0, 20, 20, 0, 0};
        int nPoints = 5;
        Polygon polygon = new Polygon(xPoints, yPoints, nPoints);
        canvas.fillPolygon(polygon);
        canvas.setColor(old2);

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