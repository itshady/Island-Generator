package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Polygon;
import Geometries.Vertex;

import java.util.ArrayList;
import java.util.List;

public class VertexDecoratorBuilder {
    Vertex vertex;
    public VertexDecoratorBuilder() {

    }

    public VertexDecoratorBuilder addVertex(Vertex v) {
        vertex = v;
        return this;
    }

    public VertexDecorator build() {
        if (vertex == null) throw new IllegalArgumentException("Vertex cannot be null.");
        return new VertexDecorator(vertex);
    }
}
