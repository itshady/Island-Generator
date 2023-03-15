package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Polygon;

import java.util.ArrayList;
import java.util.List;

public class TileBuilder {

    List<Border> borders = new ArrayList<>();
    VertexDecorator centroid;

    Polygon polygon;


    public TileBuilder() {

    }

    public TileBuilder addBorders(List<Border> borderList) {
        borders = borderList;
        return this;
    }

    public TileBuilder addPolygon(Polygon polygon) {
        this.polygon = polygon;
        return this;
    }

    public TileBuilder addCentroid(VertexDecorator centroid) {
        this.centroid = centroid;
        return this;
    }

    public Tile build() {
        if (polygon == null || borders.isEmpty() || centroid == null) throw new IllegalArgumentException("Polygon is required and borders list cannot be empty.");
        return new Tile(polygon, borders, centroid);
    }

}


