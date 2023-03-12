package ca.mcmaster.cas.se2aa4.a2.island.Containers;

import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.PolygonMapper;
import org.locationtech.jts.geom.Polygon;

import java.util.Map;

public class ADTContainer {
    private VertexSet vertexSet;
    private SegmentSet segmentSet;
    private PolygonSet polygonSet;

    private Map<Polygon, Geometries.Polygon> polygonReferences;

    public VertexSet getVertices() {
        return vertexSet;
    }

    public SegmentSet getSegments() {
        return segmentSet;
    }

    public PolygonSet getPolygons() {
        return polygonSet;
    }

    public Map<Polygon, Geometries.Polygon> getMappedPolygons() {
        return polygonReferences;
    }

    public void register(VertexSet vertices) {
        vertexSet = vertices;
    }

    public void register(SegmentSet segments) {
        segmentSet = segments;
    }

    public void register(PolygonSet polygons) {
        polygonSet = polygons;
        PolygonMapper polygonMapper = new PolygonMapper();
        polygonReferences = polygonMapper.mapPolygons(this);
    }
}
