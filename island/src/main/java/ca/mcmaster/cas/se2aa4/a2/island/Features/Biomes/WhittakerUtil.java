package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class WhittakerUtil implements DiagramProfile {

    Island island;
    public void process(Island island) {
        this.island = island;
        setBiomesBoundaries();

        // Gather the moisture and elevation data
        getBiomesBoundaries();

        setAllBiomes();

    }

    // For each profile, set the boundaries for the biomes
    abstract void setBiomesBoundaries();

    // Gather the biomes boundaries
    abstract Map<String, Map<String, Double>> getBiomesBoundaries();

    private void setAllBiomes() {
        List<Tile> landTiles = getLandTiles();


    }

    // May need to refactor, since we use this same method in LandWaterGenerator
    protected List<Tile> getLandTiles() {
        List<Tile> landTiles = new ArrayList<>();
        for (Tile tile : island.getTiles()) {
            if (tile.isLand()) {
                landTiles.add(tile);
            }
        }
        return landTiles;
    }


}
