package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Vertex;

import java.awt.*;

public class VertexDecorator {
    Vertex vertex;
    Boolean isSpring = false;
    Integer altitude;

    public boolean isSpring() {
        return isSpring;
    }

    public void setSpring(Boolean spring) {
        isSpring = spring;
    }

    public void setThickness(Float thickness) {
        vertex.setThickness(thickness);
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public static VertexDecoratorBuilder newBuilder() {
        return new VertexDecoratorBuilder();
    }

    protected VertexDecorator(Vertex vertex) {
        this.vertex = vertex;
    }

    public double getX() {
        return this.vertex.getX();
    }

    public double getY() {
        return this.vertex.getY();
    }

    public void setColor(Color color) {
        vertex.setColor(color);
    }
}
