package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class LandWaterGenerator implements BodiesOfWater {

    Island island;

    List<Tile> tiles;

    private final Integer waterSize = 5;
    List<Tile> landTiles = new ArrayList<>();

    public void process(Island island, Integer num) {
        this.island = island;
        tiles = island.getTiles();
        determineLandTiles();
        int waterNumber = setNum(num);
        // Iterate n times (user specified)
        // generateWater method

        for (int i = 0; i < waterNumber; i++) {
            generateWater();
        }
    }

    public Integer setNum(Integer num) {
        return num;
    }

    protected void determineLandTiles() {
        for (Tile tile : tiles) {
            if (tile.getType() == TileType.LAND) {
                landTiles.add(tile);
            }
        }
    }

    protected abstract void setWater(Tile tile);

    protected void generateWater() {
        Random random = new Random();
        Tile sourceTile = landTiles.get(random.nextInt(landTiles.size()));
        while(sourceTile.hasAquifer()) {
            sourceTile = landTiles.get(random.nextInt(landTiles.size()));
        }
        setWater(sourceTile);
    }
}

