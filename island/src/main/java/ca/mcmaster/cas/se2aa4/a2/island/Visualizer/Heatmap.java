package ca.mcmaster.cas.se2aa4.a2.island.Visualizer;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Contains methods that all Visualizers require
 */

public abstract class Heatmap implements Visualizer {
    private static final Map<String, Color> colors = new HashMap<>();

    // Initializes the colour map with its needed colours.
    static {
        colors.put("ocean", new Color(0,87,143,255));
    }

    /**
     * Processes all the tiles into their designated heatmap.
     * @param island
     */
    @Override
    public void process(Island island) {
        traverseTiles(island.getTiles());
        traverseBorders(island.getBorders());
        traverseVertexDecorators(island.getVertexDecorators());
    }

    /**
     * Get the color a tile should have based on what the heatmap represents
     * @param tile: Tile to get color for
     * @return: Color that a tile should have based on the heatmap property
     */
    protected abstract Color getTileColor(Tile tile);

    /**
     * Sets the tile to its designated colours.
     * @param tiles: a list of tiles
     */
    private void traverseTiles(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.isOcean()) tile.setColor(colors.get("ocean"));
            else tile.setColor(getTileColor(tile));
        }
    }

    /**
     * Sets all borders colours to black and thicknesses to zero.
     * @param borders: a list of borders
     */
    private void traverseBorders(List<Border> borders) {
        for (Border border : borders) {
            border.setColor(Color.BLACK);
            border.setThickness(0f);
        }
    }

    /**
     * Sets all vertex decorators colours to black and thicknesses to zero.
     * @param vertices: a list of vertex decorators
     */
    private void traverseVertexDecorators(List<VertexDecorator> vertices) {
        for (VertexDecorator vertex : vertices) {
            vertex.setColor(Color.BLACK);
        }
    }
}
