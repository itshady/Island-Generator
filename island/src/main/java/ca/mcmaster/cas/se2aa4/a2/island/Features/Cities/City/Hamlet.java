package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City;

import java.awt.*;

public class Hamlet extends City {
    public Hamlet() {
        super(4, new Color(191, 144, 0));
    }

    @Override
    public boolean isHamlet() {
        return true;
    }
}
