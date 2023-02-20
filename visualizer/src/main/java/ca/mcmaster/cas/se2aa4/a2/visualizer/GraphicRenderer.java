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

import java.util.List;

public class GraphicRenderer {
    private boolean debug = false;

    public void turnOnDebug() {
        debug = true;
    }

    public void turnOffDebug() {
        debug = false;
    }

    public void render(Mesh aMesh, Graphics2D canvas) {
        if (debug) {
            new DebugMode().render(aMesh, canvas);
        } else {
            new NormalMode().render(aMesh, canvas);
        }
    }

}