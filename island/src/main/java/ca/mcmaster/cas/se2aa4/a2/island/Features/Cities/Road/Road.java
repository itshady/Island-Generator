package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road;

public abstract class Road {
    private Integer multiplicity;

    public Road(Integer multiplicity) {
        this.multiplicity = multiplicity;
    }

    public Integer getMultiplicity() {
        return multiplicity;
    }
}
