package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

public class Aquifer extends Water {
    public Aquifer() {
        // make moisture number random, or based on how many lake tiles
        setMoisture(50);
        setMultiplicity(1);
    }

    public Aquifer(Integer multiplicity) {
        setMoisture(50);
        setMultiplicity(multiplicity);
    }

    public boolean isAboveGround() {
        return false;
    }

    @Override
    public boolean isAquifer() {
        return true;
    }

    @Override
    public Aquifer clone() {
        try {
            return (Aquifer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
