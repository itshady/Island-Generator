package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Lagoon;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Shape;

public class ShapeFactory implements Factory {
    @Override
    public Shape create(String s) {
        return switch (s) {
            case "lagoon" -> new Lagoon();
            case "circle" -> new Circle();
            case "oval" -> new Oval();
            default -> new Circle();
        };
    }
}
