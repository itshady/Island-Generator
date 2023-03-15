package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.util.*;

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

    protected abstract void selectWaters(Tile tile);

    protected abstract boolean meetsRequirements(Tile tile, Tile sourceTile);


    protected void setWaters(Tile tile) {
        // Based on the source, pick number from 0 to tile.getNeighbours().size() for lake size
        Random bag = new Random();
        selectWaters(tile);
        int waterSize = bag.nextInt(0, tile.getNeighbours().size());
        for (int i = 1; i < waterSize; i++) {
            // Get polygon neighbours
            Set<Integer> tileNeighbours = tile.getNeighbours();
            for (Integer id: tileNeighbours) {
                // Get the tile
                Tile neighbour = tiles.get(id);
                if (neighbour.getType() == TileType.LAND && meetsRequirements(neighbour, tile) ) {
                    selectWaters(neighbour);
                }
            }
        }
    }

    protected void generateWater() {
        Random random = new Random();
        Tile sourceTile = landTiles.get(random.nextInt(landTiles.size()));
        while(!meetsRequirements(sourceTile, sourceTile)) {
            sourceTile = landTiles.get(random.nextInt(landTiles.size()));
        }
        setWaters(sourceTile);
    }
}

