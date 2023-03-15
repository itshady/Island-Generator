package ca.mcmaster.cas.se2aa4.a2.island;

import java.awt.*;

public enum TileType {

    OCEAN(Color.BLUE), LAND(new Color(255,255,255,255)), LAKE(Color.CYAN), BEACH(Color.YELLOW);

    private final Color color;

    TileType(Color c) {
        this.color = c;
    }

    public Color toColor() {
        return color;
    }

}
