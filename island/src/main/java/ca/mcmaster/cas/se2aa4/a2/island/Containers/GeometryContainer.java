package ca.mcmaster.cas.se2aa4.a2.island.Containers;

import EnhancedSets.GeometrySet;
import EnhancedSets.VertexSet;

public interface GeometryContainer {
    Object getVertices();
    Object getSegments();
    Object getPolygons();
    //void register(GeometrySet<?> o);

//    <T> void register(T parameter);
}
