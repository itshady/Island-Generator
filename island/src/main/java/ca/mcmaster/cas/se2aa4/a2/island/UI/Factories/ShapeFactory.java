package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.*;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("circle", Circle.class);
        bindings.put("oval", Oval.class);
        bindings.put("triangle", Triangle.class);
        bindings.put("threecircle", ThreeCircle.class);
        bindings.put("lagoon", Lagoon.class);
        bindings.put("square", Square.class);
        bindings.put(DEFAULT, Circle.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            Class shapeClass = bindings.get(config.export(Configuration.SHAPE));
            Shape shape = ((Shape) shapeClass.getDeclaredConstructor().newInstance());
            shape.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
