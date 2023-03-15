package ca.mcmaster.cas.se2aa4.a2.island;

import Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.PolygonMapper;

import java.awt.*;
import java.util.Set;

public class Tile {
    Polygon polygon;
    org.locationtech.jts.geom.Polygon JTSPolygon;
    TileType type;

    public void setType(TileType type) {
        this.type = type;
    }

    public TileType getType() {
        return type;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public org.locationtech.jts.geom.Polygon getJTSPolygon() {
        return JTSPolygon;
    }

    public Tile(Polygon polygon) {
        this.polygon = polygon;
        JTSPolygon = new PolygonMapper().process(this.polygon);
    }

    public void setColor(Color color) {
        polygon.setColor(color);
    }
    public Color getColor() {
        return polygon.getColor();
    }

    public Set<Integer> getNeighbours() {
        return polygon.getPolygonNeighbours();
    }
}
