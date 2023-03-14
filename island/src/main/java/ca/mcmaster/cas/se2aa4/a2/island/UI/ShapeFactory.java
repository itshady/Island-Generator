//package ca.mcmaster.cas.se2aa4.a2.island.UI;
//
//import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.*;
//
//public class ShapeFactory implements Buildable {
//    @Override
//    public Shape create(String s) {
//        return switch (s) {
//            case "lagoon" -> new Lagoon();
//            case "circle" -> new Circle();
//            case "oval" -> new Oval();
//            case "triangle" -> new Triangle();
//            case "threecircle" -> new ThreeCircle();
//            default -> new Circle();
//        };
//    }
//}
