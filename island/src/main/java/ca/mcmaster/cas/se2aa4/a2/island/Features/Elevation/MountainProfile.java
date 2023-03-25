package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

public class MountainProfile extends ElevationUtil {

    protected Integer calculateAltitude(VertexDecorator vertex) {
        Double distanceFromCenter = getDistance(center,vertex.getVertex().getCoordinate());
        Double slope = -300/(0.7* Math.min(island.width(), island.height()));
        Integer y = Math.toIntExact(maxAltitude + (Math.round((distanceFromCenter * slope))));
        if (y <= minAltitude) return minAltitude+1;
        return y;
    }
}
