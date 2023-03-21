package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.PolygonMapper;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.Biome;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.SoilProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.BodyOfWater;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import static ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil.maxAltitude;
import static ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil.minAltitude;
import static ca.mcmaster.cas.se2aa4.a2.island.TileType.TEST;

public class Tile {
    Polygon polygon;
    List<Border> borders;
    VertexDecorator centroid;
    org.locationtech.jts.geom.Polygon JTSPolygon;
    BodyOfWater water;
    Double absorption;
    SoilProfile soilProfile;
    Biome biome;

    public void setBiome(Biome biome) {
        this.biome = biome;
        setColor(biome.toColor());
    }

    public void setWater(BodyOfWater water) {
        this.water = water;
    }

    public Integer getAltitude() {
        return centroid.getAltitude();
    }

    public boolean isLand() {
        return water == null || !water.isOcean();
    }

    public boolean isOcean() {
        return water != null && water.isOcean();
    }

    public boolean hasLake() {
        return water != null && water.isLake();
    }

    public boolean hasAquifer() {
        return water != null && water.isAquifer();
    }

    public boolean hasBodyOfWater() {
        return hasAquifer() || hasLake() || isOcean();
    }

    public VertexDecorator getCentroid() {
        return centroid;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public static TileBuilder newBuilder() {
        return new TileBuilder();
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
        if (polygon.getColor() == TEST.toColor()) return;

        if (hasAquifer()) {
            polygon.setColor(new Color(92, 255, 0));
            return;
        }
        // if ocean
        Color color;
        // ELEVATION HEATMAP
        // if land
        if (isLand() && false) { // do && false if you wanna turn off heatmap
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
        } else if (isLand() && false){ // ABSORPTION HEAT MAP - do && false if you wanna turn off heatmap
            List<Color> colors = new ArrayList<>();
            colors.add(new Color(255, 225, 255));
            colors.add(new Color(235, 185, 215));
            colors.add(new Color(227, 139, 191));
            colors.add(new Color(209, 109, 170));
            colors.add(new Color(224, 67, 164));
            colors.add(new Color(179, 66, 135));
            colors.add(new Color(187, 32, 128));
            colors.add(new Color(135, 18, 90));
            colors.add(new Color(110, 4, 71));
            colors.add(new Color(77,2,48));

            Double separation = 100.0 / colors.size();
            if (this.getAbsorption() <= separation) color = colors.get(0);
            else if (this.getAbsorption() <= separation * 2) color = colors.get(1);
            else if (this.getAbsorption() <= separation * 3) color = colors.get(2);
            else if (this.getAbsorption() <= separation * 4) color = colors.get(3);
            else if (this.getAbsorption()<= separation * 5) color = colors.get(4);
            else if (this.getAbsorption() <= separation * 6) color = colors.get(5);
            else if (this.getAbsorption() <= separation * 7) color = colors.get(6);
            else if (this.getAbsorption() <= separation * 8) color = colors.get(8);
            else color = colors.get(9);

        } else color = polygon.getColor();
        if (hasLake()) color = new Color(103,168,209,255);
        else if (isOcean()) color = new Color(0,87,143,255);
        polygon.setColor(color);
    }

    public Set<Integer> getNeighbours() {
        return polygon.getPolygonNeighbours();
    }

    @Override
    public String toString() {
        return "("+centroid.getX()+", "+centroid.getY()+")";
    }

    public BodyOfWater getWater() {
        return water;
    }

    public void setSoilProfile(SoilProfile soilProfile) {
        this.soilProfile = soilProfile;
    }

    public void setColor(Color color) {
        polygon.setColor(color);
    }

    public void setAbsorption(Double absorption) {
        this.absorption = absorption;
    }

    public Double getAbsorption() {
        return absorption;
    }

    public SoilProfile getSoilProfile() {
        return soilProfile;
    }
}
