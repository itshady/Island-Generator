package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

public class Lake extends Water implements Cloneable {
    public Lake() {
        // make moisture number random, or based on how many lake tiles
        moisture = 90;
        setMultiplicity(1);
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
