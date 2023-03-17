package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

public abstract class Water implements BodyOfWater {
    Integer moisture;
    Integer multiplicity;

    public Integer getMoisture() {
        return moisture;
    }

    public void setMultiplicity(Integer multiplicity) {
        this.multiplicity = multiplicity;
    }

    public Integer getMultiplicity() {
        return multiplicity;
    }
}
