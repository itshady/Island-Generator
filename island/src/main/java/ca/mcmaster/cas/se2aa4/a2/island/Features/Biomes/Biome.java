package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import java.awt.*;

public enum Biome {
    // USA
    DESERT(new Color(234, 150, 77,255)),
    TUNDRA(new Color(178, 233, 255,255)),
    GRASSLAND(new Color(132, 238, 105,255)),
    MIXEDFOREST(new Color(96, 138, 98,255)),
    MONTANEFOREST(new Color(0, 131, 79,255)),
    SAVANNA(new Color(255, 100, 4)),

    // REST
    RAINFOREST(new Color(0, 87, 67,255)),
    ALPINETUNDRA(new Color(201, 215, 227,255)),
    MOUNTAIN(new Color(59, 59, 59,255)),
    MANGROVE(new Color(78, 84, 11));


    private final Color color;

    Biome(Color c) {
        this.color = c;
    }

    public Color toColor() {
        return color;
    }
}
