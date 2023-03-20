package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;

public class Ocean extends Water {
    public Ocean() {
        moisture = 0;
        setMultiplicity(0);
    }

    @Override
    public boolean isOcean() {
        return true;
    }

    @Override
    public boolean isAboveGround() {
        return true;
    }

}
