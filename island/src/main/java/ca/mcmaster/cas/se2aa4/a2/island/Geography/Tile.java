package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.PolygonMapper;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.awt.Color;
import java.util.Set;
import java.util.List;

public class Tile {
    Polygon polygon;
    List<Border> borders;
    org.locationtech.jts.geom.Polygon JTSPolygon;
    TileType type;

    public static TileBuilder newBuilder() {
        return new TileBuilder();
    }

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

    protected Tile(Polygon polygon, List<Border> borderList) {
        this.polygon = polygon;
        borders = borderList;
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
