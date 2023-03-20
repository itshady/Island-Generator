package ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes;

import java.awt.*;

public enum Biomes {
    DESERT(new Color(217, 162, 78,255)),
    TUNDRA(new Color(132, 219, 248,255)),
    GRASSLAND(new Color(132, 238, 105,255)),
    RAINFOREST(new Color(7, 86, 17,255)),
    MANGROVE(new Color(85, 89, 21)),
    SAVANNA(new Color(236, 139, 9));

    private final Color color;

    Biomes(Color c) {
        this.color = c;
    }

    public Color toColor() {
        return color;
    }
}
