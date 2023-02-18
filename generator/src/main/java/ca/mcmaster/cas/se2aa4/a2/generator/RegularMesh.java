package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.Coordinate;

import java.util.ArrayList;
import java.util.List;

public abstract class RegularMesh extends Mesh {
    // Must have a function that generates the starting set of points for the Voronoi diagram to generate around.
    protected abstract List<Coordinate> generatePoints();

}
