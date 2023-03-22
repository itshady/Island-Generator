package ca.mcmaster.cas.se2aa4.a2.island.Containers;

import Geometries.Coordinate;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.List;

public class Island {
    private List<VertexDecorator> decorators;
    private List<Border> borders;
    private List<Tile> tiles;

    public List<VertexDecorator> getVertexDecorators() {
        return decorators;
    }

    public List<Border> getBorders() {
        return borders;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void register(List<VertexDecorator> vertexDecorators,  List<Border> borders, List<Tile> tiles ) {
        this.tiles = tiles;
        this.borders = borders;
        this.decorators = vertexDecorators;
    }

    public Tile getTile(Integer id) {
        return tiles.get(id);
    }

    /**
     * Returns the centre coordinate
     */
    public Coordinate center() {
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (VertexDecorator v: getVertexDecorators()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        return new Coordinate(max_x/2, max_y/2);
    }
}
