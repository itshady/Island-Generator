package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City;

import java.awt.*;

public class Capitol extends City {
    public Capitol() {
        super(9, Color.MAGENTA);
    }

    @Override
    public boolean isCapitol() {
        return true;
    }
}
