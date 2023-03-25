package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

public class CraterProfile extends ElevationUtil{

    protected Integer calculateAltitude(VertexDecorator vertex) {
        Double distanceFromCenter = getDistance(center,vertex.getVertex().getCoordinate());
        Double slope = 300/(0.7* Math.min(island.width(), island.height()));
        Integer y = Math.toIntExact(minAltitude + 1 + Math.round(distanceFromCenter * slope));
        if (y > maxAltitude) return maxAltitude;
        return y;
    }
}
