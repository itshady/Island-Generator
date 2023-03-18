package ca.mcmaster.cas.se2aa4.a2.island.Containers;

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

    public List<Border> getSegments() {
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
}
