package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;

import java.util.HashMap;
import java.util.Map;

public class AmericaProfile extends WhittakerUtil {

    // Stored in this format:
    // {Desert -> {minElevation -> value, maxElevation -> value, minMoisture -> value, maxMoisture -> value}
    @Override
    void setBiomesBoundaries() {
        boundaries.put(Biome.DESERT, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 50.0,
                0.0,
                25.0
        ));
        boundaries.put(Biome.TUNDRA, setBiomeProperty(
                ElevationUtil.minAltitude + 50.0,
                ElevationUtil.maxAltitude + 0.0,
                0.0,
                25.0
        ));
        boundaries.put(Biome.GRASSLAND, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 75.0,
                25.0,
                65.0
        ));
        boundaries.put(Biome.MIXEDFOREST, setBiomeProperty(
                75.0,
                ElevationUtil.maxAltitude + 0.0,
                25.0,
                65.0
        ));
        boundaries.put(Biome.SAVANNA, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 120.0,
                65.0,
                100.0
        ));
        boundaries.put(Biome.MONTANEFOREST, setBiomeProperty(
                ElevationUtil.minAltitude + 120.0,
                ElevationUtil.maxAltitude + 0.0,
                65.0,
                100.0
        ));

    }

    @Override
    Map<String, Double> setBiomeProperty(Double minElevation, Double maxElevation,
                                         Double minMoisture, Double maxMoisture) {
        Map<String, Double> biome = new HashMap<>();
        biome.put("minElevation", minElevation);
        biome.put("maxElevation", maxElevation);
        biome.put("minMoisture", minMoisture);
        biome.put("maxMoisture", maxMoisture);

        return biome;
    }
}
