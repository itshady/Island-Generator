package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

public class AquiferGenerator extends LandWaterGenerator {
    @Override
    protected BodyOfWater getNewWater() {
        return new Aquifer();
    }
}
