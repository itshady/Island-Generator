package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.*;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

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
        bindings.put("square", Square.class);
        bindings.put(DEFAULT, Circle.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            String value = config.export(Configuration.SHAPE);
            value = value != null ? value.toLowerCase() : null;
            Class shapeClass = bindings.get(value);
            Shape shape = ((Shape) shapeClass.getDeclaredConstructor().newInstance());
            shape.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}