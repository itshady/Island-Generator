package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.awt.*;


public class DryProfile extends SoilUtil {

    Color soilColor = new Color(94, 60, 26,255);

    private Double getAbsorptionRate() {
        return 0.20;
    }

    protected double calcRiverAbsorption(double distance, Integer riverMultiplicity, Integer riverMoisture) {
        return riverMoisture * riverMultiplicity * (Math.pow(getAbsorptionRate(), distance / 50));
    }

    protected double calcOceanAbsorption(double distance) {
        return 200 * Math.pow(getAbsorptionRate(), distance / 50);
    }

    protected double calcLandWaterAbsorption(Tile currentTile, double distance) {
        return currentTile.getWater().moisture() * Math.pow(getAbsorptionRate(), distance / 50);
    }

    public Color getSoilColor() {
        return this.soilColor;
    }

    public SoilProfile getSoilProfile() {
        return this;
    }
}
