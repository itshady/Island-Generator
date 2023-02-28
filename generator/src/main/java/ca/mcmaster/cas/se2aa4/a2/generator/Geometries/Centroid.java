package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import java.awt.*;

/**
 * Mutable Centroid: Has same properties as Vertex, but now also holds a Centroid attribute.
 */
public class Centroid extends Vertex {
    public Centroid(Double x, Double y) {
        super(x, y);
    }
    public Centroid(Double x, Double y, Color color) {
        super(x, y, color);
    }
    public Centroid(Double x, Double y, Float thickness) {
        super(x, y, thickness);
    }
    public Centroid(Double x, Double y, Color color, Float thickness) {
        super(x, y, color, thickness);
    }

    /**
     * Overriding the isCentroid method from the Vertex class
     * @return boolean
     */
    @Override
    public boolean isCentroid() {
        return true;
    }
}
