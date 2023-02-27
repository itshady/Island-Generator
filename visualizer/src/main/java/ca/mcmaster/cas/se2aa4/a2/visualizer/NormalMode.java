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

    protected boolean isDebug() {
        return false;
    }
}
