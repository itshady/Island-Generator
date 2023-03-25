package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contains methods that all Whittaker Diagrams requires
 */
public abstract class WhittakerUtil implements DiagramProfile {

    Island island;

    protected Map<Biome, Map<String, Double>> boundaries = new HashMap<>();

    /**
     * Processes the Whittaker Diagram on a given Island
     * @param island, Island
     */
    public void process(Island island) {
        this.island = island;
        setBiomesBoundaries();
        setAllBiomes();
    }

    // For each profile, set the boundaries for the biomes
    abstract void setBiomesBoundaries();

    /**
     * Iterate through all land tiles, and associate biome to a tile
     */
    private void setAllBiomes() {
        List<Tile> landTiles = getLandTiles();
        for (Tile tile: landTiles) {
            // Find associated biome for this tile
            Biome biome = checkForBiome(tile);
            tile.setBiome(biome);
        }

    }

    /**
     * Given moisture and elevation, associate a biome for that tile
     * @param tile, Island
     * @return Biome, Enum
     */
    private Biome checkForBiome(Tile tile) {
        Double elevation = tile.getAltitude() * 1.0;
        Double moisture = tile.getAbsorption();

        for (Biome biome: boundaries.keySet()) {
            Map<String, Double> properties = boundaries.get(biome);

            if (Math.round(elevation) >= properties.get("minElevation") && Math.round(elevation) <= properties.get("maxElevation")) {
                if (Math.round(moisture)  >= properties.get("minMoisture") && Math.round(moisture) <= properties.get("maxMoisture")) {
                    return biome;
                }
            }
        }

        throw new IllegalArgumentException("Either absorption or altitude is completely out of whittaker graph.");
    }

    /**
     * Stores the boundaries for each biome in the whittaker diagram in a Hashmap Data Structure
     * @param minElevation, double
     * @param maxElevation, double
     * @param minMoisture, double
     * @param maxMoisture, double
     * @return Boundaries Hashmap
     */
    protected Map<String, Double> setBiomeProperty(Double minElevation, Double maxElevation,
                                         Double minMoisture, Double maxMoisture) {
        Map<String, Double> biome = new HashMap<>();
        biome.put("minElevation", minElevation);
        biome.put("maxElevation", maxElevation);
        biome.put("minMoisture", minMoisture);
        biome.put("maxMoisture", maxMoisture);

        return biome;
    }

    /**
     * Given all tiles in the island, returns the "land" classified ones
     * @return List<Tile>
     */
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
