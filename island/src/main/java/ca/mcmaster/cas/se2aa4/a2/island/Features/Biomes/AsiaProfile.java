package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil;


public class AsiaProfile extends WhittakerUtil {

    /**
     * Sets the moisture and elevation boundaries for each biome in the whittaker diagram
     */
    @Override
    void setBiomesBoundaries() {
        // Stored in this format:
        // {Desert -> {minElevation -> value, maxElevation -> value, minMoisture -> value, maxMoisture -> value}
        boundaries.put(Biome.DESERT, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 75.0,
                0.0,
                25.0
        ));
        boundaries.put(Biome.MOUNTAIN, setBiomeProperty(
                ElevationUtil.minAltitude + 76.0,
                ElevationUtil.maxAltitude + 0.0,
                0.0,
                45.0
        ));
        boundaries.put(Biome.MANGROVE, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 75.0,
                26.0,
                55.0
        ));
        boundaries.put(Biome.ALPINETUNDRA, setBiomeProperty(
                ElevationUtil.minAltitude + 76.0,
                ElevationUtil.maxAltitude + 0.0,
                46.0,
                100.0
        ));
        boundaries.put(Biome.RAINFOREST, setBiomeProperty(
                ElevationUtil.minAltitude + 0.0,
                ElevationUtil.minAltitude + 75.0,
                56.0,
                100.0
        ));
    }
}
