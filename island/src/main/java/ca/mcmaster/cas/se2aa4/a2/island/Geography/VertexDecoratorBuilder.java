package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Vertex;

/**
 * Creation class for vertex decorators
 */
public class VertexDecoratorBuilder {
    private Vertex vertex;

    public VertexDecoratorBuilder addVertex(Vertex v) {
        vertex = v;
        return this;
    }

    public VertexDecorator build() {
        if (vertex == null) throw new IllegalArgumentException("Vertex cannot be null.");
        return new VertexDecorator(vertex);
    }
}
