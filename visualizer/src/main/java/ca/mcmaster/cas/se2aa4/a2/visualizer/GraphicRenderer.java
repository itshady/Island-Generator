package ca.mcmaster.cas.se2aa4.a2.visualizer;

import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;

import java.awt.*;

public class GraphicRenderer {
    private boolean debug = false;

    public void turnOnDebug() {
        debug = true;
    }

    public void turnOffDebug() {
        debug = false;
    }

    public void render(Mesh aMesh, Graphics2D canvas) {
        if (debug) new DebugMode().render(aMesh, canvas);
        else new NormalMode().render(aMesh, canvas);
    }

}