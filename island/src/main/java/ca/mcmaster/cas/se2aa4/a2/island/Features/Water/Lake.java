package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

public class Lake extends Water implements Cloneable {
    public Lake() {
        setMoisture(90);
        setMultiplicity(1);
    }

    public Lake(Integer multiplicity) {
        setMoisture(90);
        setMultiplicity(multiplicity);
    }

    public boolean isAboveGround() {
        return true;
    }

    @Override
    public boolean isLake() {
        return true;
    }

    @Override
    public Lake clone() {
        try {
            return (Lake) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
