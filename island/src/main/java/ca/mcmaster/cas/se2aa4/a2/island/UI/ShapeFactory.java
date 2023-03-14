package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.*;

import java.util.HashMap;
import java.util.Map;

public class ShapeFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();

    static {
        bindings.put("circle", Circle.class);
        bindings.put("oval", Oval.class);
        bindings.put("triangle", Triangle.class);
        bindings.put("threecircle", ThreeCircle.class);
        bindings.put("lagoon", Lagoon.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            Class klass = bindings.get(config.export(Configuration.SHAPE));
            System.out.println(klass);
            Shape shape = ((Shape) klass.getDeclaredConstructor().newInstance());
            System.out.println(shape);
            shape.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
