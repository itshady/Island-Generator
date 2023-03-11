package ca.mcmaster.cas.se2aa4.a2.island.Containers;

import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;

public class ADTContainer {
    private VertexSet vertexSet;
    private SegmentSet segmentSet;
    private PolygonSet polygonSet;

    public VertexSet getVertices() {
        return vertexSet;
    }

    public SegmentSet getSegments() {
        return segmentSet;
    }

    public PolygonSet getPolygons() {
        return polygonSet;
    }

    public void register(VertexSet vertices) {
        vertexSet = vertices;
    }

    public void register(SegmentSet segments) {
        segmentSet = segments;
    }

    public void register(PolygonSet polygons) {
        polygonSet = polygons;
    }
}
