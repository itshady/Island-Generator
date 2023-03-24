package ca.mcmaster.cas.se2aa4.a2.island.Visualizer;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.Biome;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiomeVisualizer implements Visualizer {
    private static final Map<String, Color> colors = new HashMap<>();

    static {
        colors.put("lake", new Color(103,168,209,255));
        colors.put("ocean", new Color(0,87,143,255));
        colors.put("river", new Color(103,168,209,255));
    }

    @Override
    public void process(Island island) {
        traverseTiles(island.getTiles());
        traverseBorders(island.getBorders());
        traverseVertexDecorators(island.getVertexDecorators());
    }

    private void traverseTiles(List<Tile> tiles) {
        for (Tile tile : tiles) {
            if (tile.hasLake()) tile.setColor(colors.get("lake"));
            else if (Arrays.asList(Biome.values()).contains(tile.getBiome()))
                tile.setColor(tile.getBiome().toColor());
            else tile.setColor(colors.get("ocean"));
        }
    }

    private void traverseBorders(List<Border> borders) {
        for (Border border : borders) {
            if (border.hasRiver()) {
                border.setColor(colors.get("river"));
                border.setThickness(border.getWater().multiplicity());
            } else {
                border.setColor(Color.BLACK);
                border.setThickness(0f);
            }
        }
    }

    private void traverseVertexDecorators(List<VertexDecorator> vertices) {
        for (VertexDecorator vertex : vertices) {
            vertex.setColor(Color.BLACK);
        }
    }
}
