package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;

public class Centroid extends Vertex {
    public Centroid(Integer id, Double x, Double y) {
        super(id, x, y);
    }
    public Centroid(Integer id, Double x, Double y, Color color) {
        super(id, x, y, color);
    }
    public Centroid(Integer id, Double x, Double y, Float thickness) {
        super(id, x, y, thickness);
    }
    public Centroid(Integer id, Double x, Double y, Color color, Float thickness) {
        super(id, x, y, color, thickness);
    }
    @Override
    public boolean isCentroid() {
        return true;
    }
}
