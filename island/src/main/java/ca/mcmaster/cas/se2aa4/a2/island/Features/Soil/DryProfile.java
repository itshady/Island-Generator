package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.awt.*;


public class DryProfile extends SoilUtil {

    private Double getAbsorptionRate() {
        return 0.20;
    }

    protected double calcRiverAbsorption(double distance, Integer riverMultiplicity, Integer riverMoisture) {
        return riverMoisture * riverMultiplicity * (Math.pow(getAbsorptionRate(), distance / 50)) / 10;
    }

    protected double calcLandWaterAbsorption(Tile currentTile, double distance) {
        return currentTile.getWater().moisture() * Math.pow(getAbsorptionRate(), distance / 50);
    }

    public SoilProfile getSoilProfile() {
        return this;
    }
}
