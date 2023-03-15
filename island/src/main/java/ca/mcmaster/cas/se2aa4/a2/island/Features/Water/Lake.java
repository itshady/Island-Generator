package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;

import java.util.Objects;
import java.util.Random;

public class Lake extends LandWaterGenerator {


    @Override
    protected void selectWaters(Tile tile) {
        tile.setType(TileType.LAKE);
    }

    @Override
    public Integer setNum(Integer num) {
        Random random = new Random();
        return random.nextInt(0, num + 1);
    }

    @Override
    protected boolean meetsRequirements(Tile tile, Tile sourceTile) {
        return (tile.getType() != TileType.LAKE && !touchesOcean(tile) && sameElevation(tile, sourceTile));
    }

    private boolean sameElevation(Tile tile, Tile sourceTile) {
        int maxAltitude = ElevationUtil.maxAltitude;
        int minAltitude = ElevationUtil.minAltitude;
        double separation = (maxAltitude-minAltitude)/5.0;
        int tileAltitude = tile.getCentroid().getAltitude();
        int sourceAltitude = sourceTile.getCentroid().getAltitude();
        return (sourceAltitude-separation <= tileAltitude && tileAltitude <= sourceAltitude+separation);
    }

    private boolean touchesOcean(Tile currentTile) {
        for (Integer idx : currentTile.getNeighbours()) {
            Tile currentNeighbour = tiles.get(idx);
            if (currentNeighbour.getType() == TileType.OCEAN) return true;
         }
        return false;
    }

}
