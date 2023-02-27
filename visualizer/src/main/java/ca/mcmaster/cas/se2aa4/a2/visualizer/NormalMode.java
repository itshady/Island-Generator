package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public class NormalMode extends VisualMode{

    protected void render(Structs.Mesh aMesh, Graphics2D canvas) {
        super.render(aMesh,canvas);
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

    protected boolean isDebug() {
        return false;
    }

}
