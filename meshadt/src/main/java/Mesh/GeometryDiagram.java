package Mesh;

import Geometries.Centroid;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.List;

public interface GeometryDiagram {
    /**
     * Generates a diagram using io.Structs Library.
     * @return Structs.Mesh.Mesh
     */
    Structs.Mesh generate();

    /**
     * Returns a list of the centroids of a Mesh.Mesh's Polygons
     * @return List of Centroids
     */
    List<Centroid> getCentroids();
}
