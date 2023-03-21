package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WhittakerUtil implements DiagramProfile {

    Island island;

    protected Map<Biome, Map<String, Double>> boundaries = new HashMap<>();
    public void process(Island island) {
        this.island = island;
        setBiomesBoundaries();
        setAllBiomes();
    }

    // For each profile, set the boundaries for the biomes
    abstract void setBiomesBoundaries();

    private void setAllBiomes() {
        List<Tile> landTiles = getLandTiles();
        for (Tile tile: landTiles) {
            // Find associated biome for this tile
            Biome biome = checkForBiome(tile);
            System.out.println(biome);
            tile.setBiome(biome);
        }

    }

    private Biome checkForBiome(Tile tile) {
        Double elevation = tile.getAltitude() * 1.0;
        Double moisture = tile.getAbsorption();

        for (Biome biome: boundaries.keySet()) {
            Map<String, Double> properties = boundaries.get(biome);

            if (elevation >= properties.get("minElevation") && elevation <= properties.get("maxElevation")) {
                if (moisture >= properties.get("minMoisture") && moisture <= properties.get("maxMoisture")) {
                    return biome;
                }
            }
        }

        return null;
    }

    protected Map<String, Double> setBiomeProperty(Double minElevation, Double maxElevation,
                                         Double minMoisture, Double maxMoisture) {
        Map<String, Double> biome = new HashMap<>();
        biome.put("minElevation", minElevation);
        biome.put("maxElevation", maxElevation);
        biome.put("minMoisture", minMoisture);
        biome.put("maxMoisture", maxMoisture);

        return biome;
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
