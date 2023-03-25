package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

public class PrairieProfile extends ElevationUtil {
    /**
     * Implementation of calculateAltitude for a prairie
     * @param vertex, VertexDecorator
     * @return Integer, represents the altitude level
     */
    protected Double calculateAltitude(VertexDecorator vertex) {
        Seed seed = Seed.getInstance();
        return (double)seed.nextInt(Math.toIntExact(Math.round(minAltitude+1)), Math.toIntExact(Math.round(minAltitude+50)));
    }
}
