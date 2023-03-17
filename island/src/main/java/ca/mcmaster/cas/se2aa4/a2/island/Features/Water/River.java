package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

public class River extends Water {

    public River() {
        // make moisture number random, or based on how many lake tiles
        moisture = 10;
    }

    public boolean aboveGround() {
        return true;
    }
}
