package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Aquifer;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SpecificationFactory {

    private static final Map<Field, Class> bindings = new HashMap<>();

    static {
        try {
            bindings.put(Configuration.class.getDeclaredField("SHAPE"), ShapeFactory.class);
            bindings.put(Configuration.class.getDeclaredField("ALTITUDE"), AltitudeFactory.class);
            bindings.put(Configuration.class.getDeclaredField("AQUIFER"), AquiferFactory.class);
//            bindings.put(Configuration.class.getDeclaredField("LAKE"), AquiferFactory.class);
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
