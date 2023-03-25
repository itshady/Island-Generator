package ca.mcmaster.cas.se2aa4.a2.island.Visualizer;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil.maxAltitude;
import static ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationUtil.minAltitude;

/**
 * Visualizer for altitudes of tiles
 */

public class AltitudeVisualizer extends Heatmap {

    /**
     * Get the color based on altitude
     * @param tile: a tile with altitude
     * @return: Color of the specific altitude value
     */
    @Override
    protected Color getTileColor(Tile tile) {
        List<Color> colors = new ArrayList<>();
        // Going down, colours go from highest altitude to lowest altitude.
        colors.add(new Color(233, 62, 58));
        colors.add(new Color(237, 104, 60));
        colors.add(new Color(243, 144, 63));
        colors.add(new Color(253, 199, 12));
        colors.add(new Color(255, 243, 59));
        Integer max = maxAltitude;
        Integer min = minAltitude;
        int separation = (max - min) / colors.size();

        // Determining colours based on altitude values.
        if (tile.getAltitude() <= min + separation) return colors.get(4);
        else if (tile.getAltitude() <= min + separation * 2) return colors.get(3);
        else if (tile.getAltitude() <= min + separation * 3) return colors.get(2);
        else if (tile.getAltitude() <= min + separation * 4) return colors.get(1);
        else return colors.get(0);
    }
}
