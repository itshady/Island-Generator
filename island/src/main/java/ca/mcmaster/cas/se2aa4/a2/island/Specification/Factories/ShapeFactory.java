package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.*;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for shape generation
 */

public class ShapeFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    // Puts all the necessary shapes into the bindings map
    static {
        bindings.put("circle", Circle.class);
        bindings.put("oval", Oval.class);
        bindings.put("triangle", Triangle.class);
        bindings.put("threecircle", ThreeCircle.class);
        bindings.put("square", Square.class);
        bindings.put(DEFAULT, Circle.class);
    }

    /**
     * Runs the designated shape class on the given island based on its configuration
     * @param island: Island to execute feature on
     * @param config: Specified shape
     */
    @Override
    public void run(Island island, Configuration config) {
        try {
            String value = config.export(Configuration.SHAPE);
            value = value != null ? value.toLowerCase() : null;
            Class shapeClass = bindings.getOrDefault(value, Circle.class);
            Shape shape = ((Shape) shapeClass.getDeclaredConstructor().newInstance());
            shape.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
