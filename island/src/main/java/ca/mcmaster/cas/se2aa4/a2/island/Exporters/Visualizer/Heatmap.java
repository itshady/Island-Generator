package ca.mcmaster.cas.se2aa4.a2.island.Exporters.Visualizer;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Heatmap implements Visualizer {
    private static final Map<String, Color> colors = new HashMap<>();

    static {
        colors.put("ocean", new Color(0,87,143,255));
    }

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

    private void traverseTiles(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.isOcean()) tile.setColor(colors.get("ocean"));
            else tile.setColor(getTileColor(tile));
        }
    }

    private void traverseBorders(List<Border> borders) {
        for (Border border : borders) {
            border.setColor(Color.BLACK);
            border.setThickness(0f);
        }
    }

    private void traverseVertexDecorators(List<VertexDecorator> vertices) {
        for (VertexDecorator vertex : vertices) {
            vertex.setColor(Color.BLACK);
        }
    }
}
