package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Visualizer.*;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.Visualizer.*;

import java.util.HashMap;
import java.util.Map;

public class VisualizerFactory implements FeatureRunner {
//    @Override
//    public void process(Island island, Configuration config) {
//        try {
//            if (config.export(Configuration.VISUAL) == null) {
//                new BiomeVisualizer();
//            }
//            String visual = config.export(Configuration.VISUAL).toLowerCase();
//            Visualizer visualizer = switch (visual) {
//                case "altitude" -> new AltitudeVisualizer();
//                case "moisture" -> new MoistureVisualizer();
//                case "debug" -> new DebugVisualizer();
//                default -> new BiomeVisualizer();
//            };
//
//            visualizer.process(island);
//        } catch (Exception e) {
//            throw new IllegalArgumentException(e);
//        }
//    }


    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("altitude", AltitudeVisualizer.class);
        bindings.put("moisture", MoistureVisualizer.class);
        bindings.put("debug", DebugVisualizer.class);
        bindings.put("biome", BiomeVisualizer.class);
        bindings.put(DEFAULT, BiomeVisualizer.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            String value = config.export(Configuration.VISUAL);
            value = value != null ? value.toLowerCase() : null;
            Class visualizerClass = bindings.get(value);
            Visualizer visualizer = ((Visualizer) visualizerClass.getDeclaredConstructor().newInstance());
            visualizer.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
