package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmericaProfile extends WhittakerUtil {

    private Map<String, Map<String, Double>> boundaries = new HashMap<>();

    // Stored in this format:
    // {Desert -> {minElevation -> value, maxElevation -> value, minMoisture -> value, maxMoisture -> value}
    @Override
    void setBiomesBoundaries() {

    }

    private Map<String, Integer> setDesertProperties() {
        Map<String, Integer> desert = new HashMap<>();

        return desert;
    }

    private Map<String, Integer> setTundraProperties() {
        Map<String, Integer> tundra = new HashMap<>();

        return tundra;
    }

    private Map<String, Integer> setGrasslandProperties() {
        Map<String, Integer> tundra = new HashMap<>();

        return tundra;
    }

    @Override
    Map<String, Map<String, Double>> getBiomesBoundaries() {
        // Shallow copy of the above hashmap
        return new HashMap<>(this.boundaries);
    }
}
