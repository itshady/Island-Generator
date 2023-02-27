package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import ca.mcmaster.cas.se2aa4.a2.generator.Geometries.Centroid;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public interface GeometryDiagram {
    /**
     * Generates a diagram using io.Structs Library.
     * @return Structs.Mesh
     */
    public Structs.Mesh generate();

    /**
     * Returns a list of the centroids of a Mesh's Polygons
     * @return List of Centroids
     */
    public List<Centroid> getCentroids();
}
