package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.locationtech.jts.geom.Coordinate;

import java.util.List;

public abstract class RegularMesh extends Mesh {
    // Must have a function that generates the starting set of points for the Voronoi diagram to generate around.
    protected abstract List<Coordinate> generatePoints();

}
