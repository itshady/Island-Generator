package ca.mcmaster.cas.se2aa4.a2.island;

import java.awt.*;

public enum TileType {

    OCEAN(new Color(0,87,143,255)), LAND(new Color(255,255,255,255)), LAKE(new Color(103,168,209,255)), BEACH(new Color(242,243,200,255));

    private final Color color;

    TileType(Color c) {
        this.color = c;
    }

    public Color toColor() {
        return color;
    }

}
