package ca.mcmaster.cas.se2aa4.a2.visualizer.Modes;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;

public class NormalMode extends VisualMode{
    public void render(Structs.Mesh aMesh, Graphics2D canvas) {
        super.render(aMesh,canvas);
    }

    protected boolean isDebug() {
        return false;
    }
}
