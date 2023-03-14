package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.ThreeCircle;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Triangle;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SpecificationFactory {

    private static final Map<Field, Class> bindings = new HashMap<>();

    static {
        try {
            bindings.put(Configuration.class.getDeclaredField("SHAPE"), ShapeFactory.class);
            bindings.put(Configuration.class.getDeclaredField("ALTITUDE"), AltitudeFactory.class);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    public static void run(Field feature, Island island, Configuration config) {
        // This code can be simplified with a switch case over the kind of mesh
        try {
            Class shapeClass = bindings.get(feature);
            FeatureRunner runner = (FeatureRunner) shapeClass.getDeclaredConstructor().newInstance();
            runner.process(island, config);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
