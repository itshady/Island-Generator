package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;

public class River extends Water {
    public River() {
        moisture = 70;
        setMultiplicity(Seed.nextInt(1,4));
    }

    public boolean isAboveGround() {
        return true;
    }

    @Override
    public boolean isRiver() {
        return true;
    }

    @Override
    public River clone() {
        try {
            return (River) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
