package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Property;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Segment;

import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import java.awt.Color;
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
//        for (Segment segment : aMesh.getSegmentsList()) {
//            Color old = canvas.getColor();
//            canvas.setColor(extractColor(segment.getPropertiesList()));
//            Line2D line = new Line2D.Double(extractV1(segment.getPropertiesList()), extractV2(segment.getPropertiesList()));
//            canvas.draw(line);
//            canvas.setColor(old);
//        }
        int counter = 0;
        for (Vertex v: aMesh.getVerticesList()) {
            System.out.println(counter++);
            double centre_x = v.getX() - (THICKNESS/2.0d);
            double centre_y = v.getY() - (THICKNESS/2.0d);
            Color old = canvas.getColor();
            canvas.setColor(extractColor(v.getPropertiesList()));
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, THICKNESS, THICKNESS);
            canvas.fill(point);
            canvas.setColor(old);
        }
//        for (int i = 0; i < aMesh.getVerticesCount()-1; i++) {
//            Color old = canvas.getColor();
//            canvas.setColor(Color.BLACK);
//            Line2D line = new Line2D.Double(aMesh.getVerticesList().get(i).getX(), aMesh.getVerticesList().get(i).getY(), aMesh.getVerticesList().get(i+1).getX(), aMesh.getVerticesList().get(i+1).getY());
//            canvas.draw(line);
//            canvas.setColor(old);
//        }

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
        String[] raw = val.substring( 1, val.length() - 1 ).split(",");  // String: [5,10]
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
        String[] raw = val.substring( 1, val.length() - 1 ).split(",");  // String: [5,10]
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
        return new Color(red, green, blue);
    }

}
