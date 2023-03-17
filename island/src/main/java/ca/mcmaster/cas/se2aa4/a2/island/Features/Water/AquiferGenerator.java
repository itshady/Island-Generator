package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

public class AquiferGenerator extends LandWaterGenerator {
    @Override
    protected void selectWaters(Tile tile) {
        tile.setAquifer(true);
    }

    @Override
    protected boolean meetsRequirements(Tile tile, Tile sourceTile) {
        return (!tile.hasAquifer() && tile.getType() != TileType.LAKE && tile.getType() != TileType.OCEAN);
    }
}
