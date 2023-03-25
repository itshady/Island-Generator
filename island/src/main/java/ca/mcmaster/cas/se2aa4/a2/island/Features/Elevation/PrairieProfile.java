package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.Random;

public class PrairieProfile extends ElevationUtil {
    /**
     * Implementation of calculateAltitude for a prairie
     * @param vertex, VertexDecorator
     * @return Integer, represents the altitude level
     */
    protected Integer calculateAltitude(VertexDecorator vertex) {
        Seed seed = Seed.getInstance();
        return seed.nextInt(minAltitude+1, minAltitude+50);
    }
}
