package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Vertex {
    private Structs.Vertex vertex;
    private Color color;
    private Integer id;
    private final double precision = 0.01;

    // Default constructor
    public Vertex() {

    }

    public boolean isCentroid() {
        return false;
    }

    public Vertex(Integer id, Double x, Double y) {
        this.id = id;
        color = generateColors();
        setVertex(x, y, color);
    }

    public Vertex(Integer id, Double x, Double y, Float thickness) {
        this.id = id;
        color = generateColors();
        setVertex(x, y, color, thickness);
    }

    public Vertex(Integer id, Integer x, Integer y) {
        this.id = id;
        color = generateColors();
        setVertex(x, y, color);
    }

    public Vertex(Integer id, Integer x, Integer y, Float thickness) {
        this.id = id;
        color = generateColors();
        setVertex(x, y, color, thickness);
    }

    public Vertex(Integer id, Double x, Double y, Color color) {
        this.id = id;
        this.color = color;
        setVertex(x, y, color);
    }

    public Vertex(Integer id, Double x, Double y, Color color, Float thickness) {
        this.id = id;
        this.color = color;
        setVertex(x, y, color, thickness);
    }

    public Vertex(Integer id, Integer x, Integer y, Color color) {
        this.id = id;
        this.color = color;
        setVertex(x, y, color);
    }

    public Vertex(Integer id, Integer x, Integer y, Color color, Float thickness) {
        this.id = id;
        this.color = color;
        setVertex(x, y, color, thickness);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer newId) {
        id = newId;
    }

    private void setVertex(Double x, Double y, Color color) {
        vertex = Structs.Vertex.newBuilder().setX(x*precision).setY(y*precision).addProperties(setColorProperty(color)).build();
    }

    private void setVertex(Double x, Double y, Color color, Float thickness) {
        vertex = Structs.Vertex.newBuilder().setX(x*precision).setY(y*precision).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
    }

    private void setVertex(Integer x, Integer y, Color color) {
        vertex = Structs.Vertex.newBuilder().setX(x*precision).setY(y*precision).addProperties(setColorProperty(color)).build();
    }

    private void setVertex(Integer x, Integer y, Color color, Float thickness) {
        vertex = Structs.Vertex.newBuilder().setX(x*precision).setY(y*precision).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).build();
    }

    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        Structs.Property colorProperty = Structs.Property.newBuilder().setKey("rgb_color").setValue(colorStr).build();
        return colorProperty;
    }

    private Structs.Property setThicknessProperty(Float thickness) {
        String vertexThickness = Float.toString(thickness);
        Structs.Property thicknessProperty = Structs.Property.newBuilder().setKey("vertex_thickness").setValue(vertexThickness).build();
        return thicknessProperty;
    }

    private Color generateColors() {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        int alpha = bag.nextInt(1,255);
        return new Color(red, green, blue, alpha);
    }
}