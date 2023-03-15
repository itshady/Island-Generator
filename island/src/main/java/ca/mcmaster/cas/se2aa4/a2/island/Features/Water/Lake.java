package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

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

}
