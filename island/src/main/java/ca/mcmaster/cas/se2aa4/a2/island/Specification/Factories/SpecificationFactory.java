package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SpecificationFactory {

    private static final Map<Field, Class> bindings = new HashMap<>();

    static {
        try {
            bindings.put(Configuration.class.getDeclaredField("SEED"), SeedFactory.class);
            bindings.put(Configuration.class.getDeclaredField("SHAPE"), ShapeFactory.class);
            bindings.put(Configuration.class.getDeclaredField("ALTITUDE"), AltitudeFactory.class);
            bindings.put(Configuration.class.getDeclaredField("AQUIFER"), AquiferFactory.class);
            bindings.put(Configuration.class.getDeclaredField("LAKE"), LakeFactory.class);
            bindings.put(Configuration.class.getDeclaredField("RIVER"), RiverFactory.class);
            bindings.put(Configuration.class.getDeclaredField("SOIL"), SoilFactory.class);
            bindings.put(Configuration.class.getDeclaredField("BIOME"), BiomeFactory.class);
            bindings.put(Configuration.class.getDeclaredField("VISUAL"), VisualizerFactory.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(Field feature, Island island, Configuration config) {
        // This code can be simplified with a switch case over the kind of mesh
        try {
            Class featureClass = bindings.get(feature);
            FeatureRunner runner = (FeatureRunner) featureClass.getDeclaredConstructor().newInstance();
            runner.process(island, config);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
