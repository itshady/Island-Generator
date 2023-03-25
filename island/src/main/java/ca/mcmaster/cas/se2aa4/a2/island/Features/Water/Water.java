package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

/**
 * Data that all bodies of water should possess
 */
public abstract class Water implements BodyOfWater {
    Integer moisture;
    Integer multiplicity;

    protected void setMoisture(Integer moisture) {
        this.moisture = moisture;
    }

    public Integer moisture() {
        return moisture;
    }

    protected void setMultiplicity(Integer multiplicity) {
        this.multiplicity = multiplicity;
    }

    public Integer multiplicity() {
        return multiplicity;
    }

    public abstract boolean isAboveGround();

    /**
     * These are all defaults which can be overridden by subclass
     */
    public boolean isLake() {
        return false;
    }

    public boolean isRiver() {
        return false;
    }

    public boolean isAquifer() {
        return false;
    }

    public boolean isOcean() {
        return false;
    }
}
