package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class Vertex {
    private Structs.Vertex vertex;
    private final Color color;
    private Integer id;
    private final Coordinate coordinate;

    public boolean isCentroid() {
        return false;
    }

    public Vertex(Integer id, Double x, Double y) {
        this.id = id;
        color = generateColors();
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color);
    }

    public Vertex(Integer id, Double x, Double y, Float thickness) {
        this.id = id;
        color = generateColors();
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color, thickness);
    }

    public Vertex(Integer id, Double x, Double y, Color color) {
        this.id = id;
        this.color = color;
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color);
    }

    public Vertex(Integer id, Double x, Double y, Color color, Float thickness) {
        this.id = id;
        this.color = color;
        coordinate = new Coordinate(x,y);
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
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y).addProperties(setColorProperty(color)).addProperties(setCentroidProperty()).build();
    }

    private void setVertex(Double x, Double y, Color color, Float thickness) {
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y).addProperties(setColorProperty(color)).addProperties(setThicknessProperty(thickness)).addProperties(setCentroidProperty()).build();
    }

    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        Structs.Property colorProperty = Structs.Property.newBuilder().setKey("rgba_color").setValue(colorStr).build();
        return colorProperty;
    }

    private Structs.Property setCentroidProperty() {
        String isCentroid = ""+isCentroid();
        return Structs.Property.newBuilder().setKey("is_centroid").setValue(isCentroid).build();
    }

    private Structs.Property setThicknessProperty(Float thickness) {
        String vertexThickness = Float.toString(thickness);
        return Structs.Property.newBuilder().setKey("thickness").setValue(vertexThickness).build();
    }

    private Color generateColors() {
        Random bag = new Random();
        int red = bag.nextInt(255);
        int green = bag.nextInt(255);
        int blue = bag.nextInt(255);
        int alpha = bag.nextInt(1,255);
        return new Color(red, green, blue, alpha);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(coordinate, vertex.coordinate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinate);
    }
}
