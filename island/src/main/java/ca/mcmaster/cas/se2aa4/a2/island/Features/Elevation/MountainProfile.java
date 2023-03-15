package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Vertex;

public class MountainProfile extends ElevationUtil {

    protected Integer calculateAltitude(Vertex vertex) {
        Double distanceFromCenter = getDistance(center,vertex.getCoordinate());
        Integer slope = -1;
        Integer y = maxAltitude + (int)(distanceFromCenter*slope);
        if (y < minAltitude) return minAltitude;
        return y;
    }
}
