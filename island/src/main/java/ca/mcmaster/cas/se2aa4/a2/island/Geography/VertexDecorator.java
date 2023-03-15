package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Vertex;

public class VertexDecorator {

    Vertex vertex;

    Boolean isSpring = false;


    public Vertex getVertex() {
        return vertex;
    }

    public static VertexDecoratorBuilder newBuilder() {
        return new VertexDecoratorBuilder();
    }


    protected VertexDecorator(Vertex vertex) {
        this.vertex = vertex;
    }

    public void setIsSpring(Boolean isSpring) {
        this.isSpring = isSpring;
    }

    public double getX() {
        return this.vertex.getX();
    }

    public double getY() {
        return this.vertex.getY();
    }



}
