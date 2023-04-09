package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road;

import java.awt.*;

/**
 * A Road is a property that a border can hold.
 * They contain a multiplicity (thickness) and a color.
 */
public abstract class Road implements Cloneable {
    private final Integer multiplicity;
    private final Color color;

    public Road(Integer multiplicity, Color color) {
        this.multiplicity = multiplicity;
        this.color = color;
    }

    public Integer getMultiplicity() {
        return multiplicity;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Road clone() {
        try {
            Road clone = (Road) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
