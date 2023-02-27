package ca.mcmaster.cas.se2aa4.a2.visualizer.Modes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;

/**
 * Renders the vertices, segments and polygons according to default colour, thickness and alpha values.
 */

public class NormalMode extends VisualMode{
    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        super.render(aMesh,canvas);
    }

    protected boolean isDebug() {
        return false;
    }
}
