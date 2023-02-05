package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Vertex {
    private Structs.Vertex vertex;
    private Color color;
    private final double precision = 0.01;

    public Vertex(Double x, Double y) {
        color = generateColors();
        setVertex(x, y, color);
    }

    public Vertex(Long x, Long y) {
        color = generateColors();
        setVertex(x, y, color);
    }

    public Vertex(Double x, Double y, Color color) {
        this.color = color;
        setVertex(x, y, color);
    }

    public Vertex(Long x, Long y, Color color) {
        this.color = color;
        setVertex(x, y, color);
    }

    public Structs.Vertex getVertex() {
        return vertex;
    }

    public Double getX() {
        return vertex.getX();
    }

    public Double getY() {
        return vertex.getY();
    }

    public Color getColor() {
        return color;
    }

    private void setVertex(Double x, Double y, Color color) {
        vertex = Structs.Vertex.newBuilder().setX(x*precision).setY(y*precision).addProperties(setColorProperty(color)).build();
    }

    private void setVertex(Long x, Long y, Color color) {
        vertex = Structs.Vertex.newBuilder().setX(x*precision).setY(y*precision).addProperties(setColorProperty(color)).build();
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
