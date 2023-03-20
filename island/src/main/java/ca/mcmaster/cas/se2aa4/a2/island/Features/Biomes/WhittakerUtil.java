package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;

import java.util.List;
import java.util.Map;

public abstract class WhittakerUtil implements DiagramProfile {

    Island island;
    public void process(Island island) {
        this.island = island;
        setBiomesBoundaries();

        // Iterate through each land tile

        // Gather the moisture and elevation data



    }

    // For each profile, set the boundaries for the biomes
    abstract void setBiomesBoundaries();

    // Gather the biomes boundaries
    // Stored in this format: {Desert -> {minElevation -> min, maxElevation -> max, minMoisture -> min, maxMoisture -> max}
    abstract Map<String, Map<String, Double>> getBiomesBoundaries();


}
