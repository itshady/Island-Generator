package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Vertex {
    private Structs.Vertex vertex;
    private Color color;

    public Vertex(int x, int y) {
        color = generateColors();
        Structs.Property colorProperty = setColorProperty(color);
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y).addProperties(colorProperty).build();
    }

    public Vertex(int x, int y, Color color) {
        this.color = color;
        Structs.Property colorProperty = setColorProperty(color);
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y).addProperties(colorProperty).build();
    }

    public Structs.Vertex getVertex() {
        return vertex;
    }

    public Color getColor() {
        return color;
    }

    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue();
        Structs.Property colorProperty = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorStr).build();
        return colorProperty;
    }

    private Color generateColors() {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        return new Color(red, green, blue);
    }
}
