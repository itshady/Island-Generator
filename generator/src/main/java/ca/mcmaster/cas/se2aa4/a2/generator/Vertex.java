package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.*;
import java.util.Objects;

public class Vertex {
    private Structs.Vertex vertex;
    private final Color color;
    private Integer id;
    private final Coordinate coordinate;
    private final PropertyHandler propertyHandler = new PropertyHandler();

    public boolean isCentroid() {
        return false;
    }

    public Vertex(Double x, Double y) {
        color = propertyHandler.generateColors();
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color);
    }

    public Vertex(Double x, Double y, Float thickness) {
        color = propertyHandler.generateColors();
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color, thickness);
    }

    public Vertex(Double x, Double y, Color color) {
        this.color = color;
        coordinate = new Coordinate(x,y);
        setVertex(x, y, color);
    }

    public Vertex(Double x, Double y, Color color, Float thickness) {
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
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y)
                .addProperties(propertyHandler.setColorProperty(color))
                .addProperties(propertyHandler.setCentroidProperty(isCentroid()))
                .build();
    }

    private void setVertex(Double x, Double y, Color color, Float thickness) {
        vertex = Structs.Vertex.newBuilder().setX(x).setY(y)
                .addProperties(propertyHandler.setColorProperty(color))
                .addProperties(propertyHandler.setThicknessProperty(thickness))
                .addProperties(propertyHandler.setCentroidProperty(isCentroid()))
                .build();
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
