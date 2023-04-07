package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.City;

import java.awt.*;

/**
 * VertexDecorator holds a mesh Vertex and adds additional properties to it
 */
public class VertexDecorator {
    Vertex vertex;
    Boolean isSpring = false;
    Double altitude;
    City city;
    boolean isCentroid = false;

    public void setCentroid(boolean centroid) {
        isCentroid = centroid;
    }

    public boolean isCentroid() {
        return isCentroid;
    }

    public boolean isCity() {
        return city != null;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }

    public boolean isSpring() {
        return isSpring;
    }

    public void setSpring(Boolean spring) {
        isSpring = spring;
    }

    public void setThickness(float thickness) {
        vertex.setThickness(thickness);
    }

    public Vertex getVertex() {
        return vertex;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
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
