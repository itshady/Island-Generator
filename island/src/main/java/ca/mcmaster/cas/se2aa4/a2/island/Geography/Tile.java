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
    boolean isWaterCenter = false;

    protected Tile(Polygon polygon, List<Border> borderList, VertexDecorator centroid) {
        this.polygon = polygon;
        borders = borderList;
        JTSPolygon = new PolygonMapper().process(this.polygon);
        this.centroid = centroid;
    }

    public boolean isWaterCenter() {
        return isWaterCenter;
    }

    public void setWaterCenter(boolean isWaterCenter) {
        this.isWaterCenter = isWaterCenter;
    }

    public void setBiome(Biome biome) {
        this.biome = biome;
    }

    public Biome getBiome() {
        return biome;
    }

    public void setAltitude(Integer altitude) {
        // error handling for out of boundary altitudes
        if (altitude < minAltitude) centroid.setAltitude(minAltitude);
        else if (altitude > maxAltitude) centroid.setAltitude(maxAltitude);
        else centroid.setAltitude(altitude);
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

    public Set<Integer> getNeighbours() {
        return polygon.getPolygonNeighbours();
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

    @Override
    public String toString() {
        return "("+centroid.getX()+", "+centroid.getY()+")";
    }
}
