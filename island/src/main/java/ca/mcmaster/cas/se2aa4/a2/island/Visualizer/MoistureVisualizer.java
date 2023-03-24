package ca.mcmaster.cas.se2aa4.a2.island.Visualizer;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoistureVisualizer extends Heatmap {
    /**
     * Get the color based on moisture
     * @param tile: a tile with moisture
     * @return: Color of the specific moisture value
     */
    @Override
    protected Color getTileColor(Tile tile) {
        List<Color> colors = new ArrayList<>();
        colors.add(new Color(255, 255, 255));
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
        if (tile.getAbsorption() <= separation) return colors.get(0);
        else if (tile.getAbsorption() <= separation * 2) return colors.get(1);
        else if (tile.getAbsorption() <= separation * 3) return colors.get(2);
        else if (tile.getAbsorption() <= separation * 4) return colors.get(3);
        else if (tile.getAbsorption()<= separation * 5) return colors.get(4);
        else if (tile.getAbsorption() <= separation * 6) return colors.get(5);
        else if (tile.getAbsorption() <= separation * 7) return colors.get(6);
        else if (tile.getAbsorption() <= separation * 8) return colors.get(8);
        else return colors.get(9);
    }
}
