package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Visualizer.*;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

public class VisualizerFactory implements FeatureRunner {
    @Override
    public void process(Island island, Configuration config) {
        try {
            String visual = config.export(Configuration.VISUAL).toLowerCase();
            Visualizer visualizer = switch (visual) {
                case "altitude" -> new AltitudeVisualizer();
                case "moisture" -> new MoistureVisualizer();
                case "debug" -> new DebugVisualizer();
                default -> new BiomeVisualizer();
            };

            visualizer.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
