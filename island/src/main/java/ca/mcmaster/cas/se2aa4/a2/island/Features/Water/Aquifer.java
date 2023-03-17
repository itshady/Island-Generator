package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

public class Aquifer extends Water {
    public Aquifer() {
        // make moisture number random, or based on how many lake tiles
        moisture = 10;
    }

    public boolean aboveGround() {
        return false;
    }
}
