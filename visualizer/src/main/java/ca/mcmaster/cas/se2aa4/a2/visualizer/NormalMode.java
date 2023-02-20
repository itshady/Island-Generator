package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class NormalMode extends VisualMode{

    protected void render(Structs.Mesh aMesh, Graphics2D canvas) {
        Stroke stroke = new BasicStroke(0.5f);
        canvas.setStroke(stroke);
        List<Structs.Vertex> vertexList = aMesh.getVerticesList();

        // Visualizing Segments
        visualizeSegments(aMesh, canvas, vertexList);

        // Visualizing Vertices
        visualizeVertices(aMesh, canvas);
    }

    protected void visualizeSegments (Structs.Mesh aMesh, Graphics2D canvas, List<Structs.Vertex> vertexList) {
        for (Structs.Segment s: aMesh.getSegmentsList()) {
            Stroke segmentStroke = new BasicStroke(extractThickness(s.getPropertiesList()));
            canvas.setStroke(segmentStroke);
            Structs.Vertex v1 = vertexList.get(s.getV1Idx());
            Structs.Vertex v2 = vertexList.get(s.getV2Idx());
            Point2D point1 = new Point2D.Double(v1.getX(), v1.getY());
            Point2D point2 = new Point2D.Double(v2.getX(), v2.getY());
            Color old = canvas.getColor();
            canvas.setColor(extractColor(s.getPropertiesList()));
            Line2D line = new Line2D.Double(point1, point2);
            canvas.draw(line);
            canvas.setColor(old);
        }
    }

    protected void visualizeVertices(Structs.Mesh aMesh, Graphics2D canvas) {
        for (Structs.Vertex v : aMesh.getVerticesList()) {
            float vertexThickness = extractThickness(v.getPropertiesList());
            double centre_x = v.getX() - (vertexThickness / 2.0d);
            double centre_y = v.getY() - (vertexThickness / 2.0d);
            Color old = canvas.getColor();
            if (isCentroid(v.getPropertiesList())) {
                continue;
            } else {
                canvas.setColor(extractColor(v.getPropertiesList()));
            }
            Ellipse2D point = new Ellipse2D.Double(centre_x, centre_y, vertexThickness, vertexThickness);
            canvas.fill(point);
            canvas.setColor(old);
        }
    }
}
