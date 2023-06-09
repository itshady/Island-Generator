package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City;

import java.awt.*;

/**
 * A City is a property that a vertex can hold.
 * They contain a multiplicity (radius) and a color.
 */
public abstract class City implements Cloneable {
    private Integer multiplicity;
    private Color color;

    public City(Integer multiplicity, Color color) {
        this.multiplicity = multiplicity;
        this.color = color;
    }

    public Integer getMultiplicity() {
        return multiplicity;
    }

    public void setMultiplicity(Integer multiplicity) {
        this.multiplicity = multiplicity;
    }

    public Color getColor() {
        return color;
    }

    public boolean isCapitol() {
        return false;
    }

    public boolean isVillage() {
        return false;
    }

    public boolean isHamlet() {
        return false;
    }

    @Override
    public City clone() {
        try {
            City clone = (City) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
