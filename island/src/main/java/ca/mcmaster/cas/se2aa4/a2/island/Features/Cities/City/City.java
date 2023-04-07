package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City;

import java.awt.*;

public abstract class City implements Cloneable {
    private Integer multiplicity;
    private final Color color = Color.DARK_GRAY;

    public City(Integer multiplicity) {
        this.multiplicity = multiplicity;
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
