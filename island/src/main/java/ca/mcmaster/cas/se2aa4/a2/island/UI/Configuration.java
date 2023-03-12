package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Lagoon;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Shape;

public class Configuration {
    Shape shape;
    public Configuration() {
        // sets defaults
        this.shape = new Circle();
    }

    public Shape getShape() {
        return shape;
    }

    public Configuration setShape(String shape) {
        ShapeFactory shapeFactory = new ShapeFactory();
        this.shape = shapeFactory.create(shape);
        return this;
    }
}
