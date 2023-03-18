package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DryProfile extends SoilUtil {

    Color soilColor = new Color(94, 60, 26,255);

    public Double getAbsorptionRate() {
        return 0.20;
    }

    public Color getSoilColor() {
        return this.soilColor;
    }

    public SoilProfile getSoilProfile() {
        return this;
    }
}
