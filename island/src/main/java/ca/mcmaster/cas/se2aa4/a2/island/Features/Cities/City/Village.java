package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City;

import java.awt.*;

public class Village extends City {
    public Village() {
        super(5, Color.RED);
    }

    @Override
    public boolean isVillage() {
        return true;
    }
}
