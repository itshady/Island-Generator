package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public abstract class RegularMesh extends Mesh {
    /**
     * Function that generates the starting set of points for the Voronoi diagram to generate around.
     * This must be a calculated set of points that represent the centroid of tessellation geometries.
     */
    protected abstract List<Coordinate> generatePoints();

}
