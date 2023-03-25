package ca.mcmaster.cas.se2aa4.a2.island.Island;

import Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Island which holds the tiles, borders and decorators
 */

public class Island {
    private List<VertexDecorator> decorators;
    private List<Border> borders;
    private List<Tile> tiles;
    private double width;
    private double height;
    private Coordinate center;

    /**
     * Returns a shallow copy of the vertex decorators
     * This is so that no one can update the current island without regenerating center, width and height
     */
    public List<VertexDecorator> getVertexDecorators() {
        return new ArrayList<>(decorators);
    }

    /**
     * Returns a shallow copy of the borders
     * This is so that no one can update the current island without regenerating center, width and height
     */
    public List<Border> getBorders() {
        return new ArrayList<>(borders);
    }

    /**
     * Returns a shallow copy of the tiles
     * This is so that no one can update the current island without regenerating center, width and height
     */
    public List<Tile> getTiles() {
        return new ArrayList<>(tiles);
    }

    public void register(List<VertexDecorator> vertexDecorators,  List<Border> borders, List<Tile> tiles) {
        this.tiles = tiles;
        this.borders = borders;
        this.decorators = vertexDecorators;
        setProperties();
    }

    /**
     * Sets the geometric properties of the mesh such as centre point, height and width
     */
    private void setProperties() {
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        double min_x = Double.MAX_VALUE;
        double min_y = Double.MAX_VALUE;
        for (VertexDecorator v: getVertexDecorators()) {
            max_x = (Double.compare(max_x, v.getX()) < 0 ? v.getX() : max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0 ? v.getY() : max_y);

            min_x = (Double.compare(min_x, v.getX()) > 0 ? v.getX() : min_x);
            min_y = (Double.compare(min_y, v.getY()) > 0 ? v.getY() : min_y);
        }
        width = Math.abs(max_x - min_x);
        height = Math.abs(max_y - min_y);
        center = new Coordinate((max_x+min_x)/2, (max_y+min_y)/2);
    }

    public Tile getTile(Integer id) {
        return tiles.get(id);
    }

    public Coordinate center() {
        return this.center;
    }

    public Double width() {
        return this.width;
    }

    public Double height() {
        return this.height;
    }

    /**
     * Given all tiles in the island, returns the "land" classified ones
     * @return List<Tile>
     */
    public List<Tile> getLandTiles() {
        List<Tile> landTiles = new ArrayList<>();
        for (Tile tile : this.getTiles()) {
            if (tile.isLand()) {
                landTiles.add(tile);
            }
        }
        return landTiles;
    }
}
