package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.util.Random;

public class Centroid extends Vertex{

    public Centroid(Integer id, Double x, Double y, Color color) {
        super(id, x, y, color);
    }
    @Override
    public boolean isCentroid() {
        return true;
    }
}
