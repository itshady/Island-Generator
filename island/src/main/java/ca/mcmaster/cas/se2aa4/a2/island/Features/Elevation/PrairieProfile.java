package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.Random;

public class PrairieProfile extends ElevationUtil {
    protected Integer calculateAltitude(VertexDecorator vertex) {
        return Seed.nextInt(minAltitude+1, maxAltitude);
    }
}
