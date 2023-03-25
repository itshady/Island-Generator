package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;

import java.util.HashMap;
import java.util.Map;

public class AmericaProfile extends WhittakerUtil {

    /**
     * Sets the moisture and elevation boundaries for each biome in the whittaker diagram
     */
    @Override
    void setBiomesBoundaries() {
        // Stored in this format:
        // {Desert -> {minElevation -> value, maxElevation -> value, minMoisture -> value, maxMoisture -> value}
        boundaries.put(Biome.DESERT, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 50.0,
                0.0,
                25.0
        ));
        boundaries.put(Biome.TUNDRA, setBiomeProperty(
                ElevationUtil.minAltitude + 51.0,
                ElevationUtil.maxAltitude + 0.0,
                0.0,
                25.0
        ));
        boundaries.put(Biome.GRASSLAND, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 75.0,
                26.0,
                65.0
        ));
        boundaries.put(Biome.MIXEDFOREST, setBiomeProperty(
                ElevationUtil.minAltitude + 76.0,
                ElevationUtil.maxAltitude + 0.0,
                26.0,
                65.0
        ));
        boundaries.put(Biome.SAVANNA, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 120.0,
                66.0,
                100.0
        ));
        boundaries.put(Biome.MONTANEFOREST, setBiomeProperty(
                ElevationUtil.minAltitude + 121.0,
                ElevationUtil.maxAltitude + 0.0,
                66.0,
                100.0
        ));

    }
}
