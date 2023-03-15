package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Vertex;

import java.util.Random;

public class PrairieProfile extends ElevationUtil {
    protected Integer calculateAltitude(Vertex vertex) {
        Random bag = new Random();
        return bag.nextInt(minAltitude+1, maxAltitude);
    }
}
