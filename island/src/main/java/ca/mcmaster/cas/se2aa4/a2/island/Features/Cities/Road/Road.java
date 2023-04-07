package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road;

import java.awt.*;

public abstract class Road {
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
}
