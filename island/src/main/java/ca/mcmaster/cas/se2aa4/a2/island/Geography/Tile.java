package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.PolygonMapper;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import static ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil.maxAltitude;
import static ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil.minAltitude;

public class Tile {
    Polygon polygon;
    List<Border> borders;
    VertexDecorator centroid;
    org.locationtech.jts.geom.Polygon JTSPolygon;
    TileType type;

    boolean hasAquifer = false;

    public VertexDecorator getCentroid() {
        return centroid;
    }

    public List<Border> getBorders() {
        return borders;
    }

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

    protected Tile(Polygon polygon, List<Border> borderList, VertexDecorator centroid) {
        this.polygon = polygon;
        borders = borderList;
        JTSPolygon = new PolygonMapper().process(this.polygon);
        this.centroid = centroid;
    }

    public void enhancePolygon() {
        // if ocean
        Color color;
        // if land
        if (type == TileType.LAND) { // do && false if you wanna turn off heatmap
            List<Color> colors = new ArrayList<>();
            colors.add(new Color(233, 62, 58));
            colors.add(new Color(237, 104, 60));
            colors.add(new Color(243, 144, 63));
            colors.add(new Color(253, 199, 12));
            colors.add(new Color(255, 243, 59));

            Integer max = maxAltitude;
            Integer min = minAltitude;
            int separation = (max - min) / colors.size();

            if (centroid.getAltitude() <= min + separation) color = colors.get(4);
            else if (centroid.getAltitude() <= min + separation * 2) color = colors.get(3);
            else if (centroid.getAltitude() <= min + separation * 3) color = colors.get(2);
            else if (centroid.getAltitude() <= min + separation * 4) color = colors.get(1);
            else color = colors.get(0);
        } else color = type.toColor();
        polygon.setColor(color);
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
