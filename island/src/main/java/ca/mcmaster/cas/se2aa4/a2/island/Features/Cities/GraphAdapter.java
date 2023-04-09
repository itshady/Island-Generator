package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;

import java.util.List;

public interface GraphAdapter<T, E> {
    /**
     * Translate elements T to nodes and elements E to edges
     * @param vertices
     * @param borders
     * @return Graph
     */
    GraphADT translate(List<T> vertices, List<E> borders);
}
