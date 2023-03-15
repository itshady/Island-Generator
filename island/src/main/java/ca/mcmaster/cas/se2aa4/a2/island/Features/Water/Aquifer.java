package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.awt.*;
import java.util.Random;
import java.util.Set;

public class Aquifer extends LandWaterGenerator {


    public void process(Island island, Tile tile) {
        this.island = island;
    }


    @Override
    protected void setWater(Tile tile) {
        // Based on the source, pick number from 0 to tile.getNeighbours().size() for lake size
        Random bag = new Random();
        int aquiferSize = bag.nextInt(0, tile.getNeighbours().size());
        for (int i = 0; i < aquiferSize; i++) {
            // Get polygon neighbours
            Set<Integer> tileNeighbours = tile.getNeighbours();
            for (Integer id: tileNeighbours) {
                // Get the tile
                Tile neighbour = tiles.get(id);
                if (neighbour.getType() == TileType.LAND && !neighbour.hasAquifer()) {
                    neighbour.setAquifer(true);
                    tile.setColor(Color.GREEN);
                }
            }
        }
    }
}
