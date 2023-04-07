package ca.mcmaster.cas.se2aa4.a2.island.Geography;

import Geometries.Vertex;

/**
 * Creation class for vertex decorators
 */
public class VertexDecoratorBuilder {
    private Vertex vertex;
    private boolean isCentroid = false;

    public VertexDecoratorBuilder addVertex(Vertex v) {
        vertex = v;
        return this;
    }

    public VertexDecoratorBuilder setCentroid(boolean centroid) {
        isCentroid = centroid;
        return this;
    }

    public VertexDecorator build() {
        if (vertex == null) throw new IllegalArgumentException("Vertex cannot be null.");
        VertexDecorator build = new VertexDecorator(vertex);
        build.setCentroid(isCentroid);
        return build;
    }
}
