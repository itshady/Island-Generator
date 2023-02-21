package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;

public class Centroid extends Vertex {
    public Centroid(Integer id, Double x, Double y) {
        super(x, y);
    }
    public Centroid(Integer id, Double x, Double y, Color color) {
        super(x, y, color);
    }
    public Centroid(Integer id, Double x, Double y, Float thickness) {
        super(x, y, thickness);
    }
    public Centroid(Integer id, Double x, Double y, Color color, Float thickness) {
        super(x, y, color, thickness);
    }
    @Override
    public boolean isCentroid() {
        return true;
    }
}
