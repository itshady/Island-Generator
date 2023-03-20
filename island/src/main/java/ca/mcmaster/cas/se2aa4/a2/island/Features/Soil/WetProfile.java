package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.awt.*;

public class WetProfile extends SoilUtil {

    private Double getAbsorptionRate() {
        return 0.50;
    }

    protected double calcRiverAbsorption(double distance, Integer riverMultiplicity, Integer riverMoisture) {
        return riverMoisture * riverMultiplicity * (Math.pow(getAbsorptionRate(), distance / 50)) / 10;
    }

    protected double calcLandWaterAbsorption(Tile currentTile, double distance) {
        return currentTile.getWater().moisture() * 3 * Math.pow(getAbsorptionRate(), distance / 50);
    }

    public SoilProfile getSoilProfile() {
        return this;
    }

}
