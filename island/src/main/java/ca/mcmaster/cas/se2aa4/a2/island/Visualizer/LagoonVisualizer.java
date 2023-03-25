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
 * Adds water sources on top of current colors in island (Sandbox mode)
 */
public class LagoonVisualizer implements Visualizer {
    private static final Map<String, Color> colors = new HashMap<>();

    // Initializes the colour map with its needed colours.
    static {
        colors.put("lagoon", new Color(103,168,209,255));
        colors.put("ocean", new Color(0,87,143,255));
    }

    /**
     * Processes all the tiles to the lagoon mode.
     * @param island: Given island to process
     */
    @Override
    public void process(Island island) {
        traverseTiles(island.getTiles());
        traverseBorders(island.getBorders());
        traverseVertexDecorators(island.getVertexDecorators());
    }

    /**
     * Sets the tile to its designated colours.
     * @param tiles: a list of tiles
     */
    private void traverseTiles(java.util.List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.hasLake()) tile.setColor(colors.get("lagoon"));
            else if (tile.isOcean()) tile.setColor(colors.get("ocean"));
        }
    }

    /**
     * Sets all borders colours to black and thicknesses to zero.
     * @param borders: a list of borders
     */
    private void traverseBorders(java.util.List<Border> borders) {
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
