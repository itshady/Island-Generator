package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;

public class LakeGenerator extends LandWaterGenerator {
    protected BodyOfWater getNewWater() {
        return new Lake();
    }
}
