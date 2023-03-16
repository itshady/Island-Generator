package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.Random;

public class PrairieProfile extends ElevationUtil {
    protected Integer calculateAltitude(VertexDecorator vertex) {
        Random bag = new Random(Seed.SEED);
        return bag.nextInt(minAltitude+1, maxAltitude);
    }
}
