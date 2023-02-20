package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;

public abstract class VisualMode {

    protected abstract void render(Structs.Mesh aMesh, Graphics2D canvas);

    protected abstract void visualizeVertices(Structs.Mesh aMesh, Graphics2D canvas);

    protected abstract void visualizeSegments(Structs.Mesh aMesh, Graphics2D canvas, List<Structs.Vertex> vertexList);

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
