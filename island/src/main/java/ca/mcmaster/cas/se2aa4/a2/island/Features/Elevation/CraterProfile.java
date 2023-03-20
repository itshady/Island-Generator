package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

public class CraterProfile extends ElevationUtil{

    protected Integer calculateAltitude(VertexDecorator vertex) {
        Double distanceFromCenter = getDistance(center,vertex.getVertex().getCoordinate());
        Integer slope = 1;
        Integer y = minAltitude + 1 + (int)(distanceFromCenter*slope);
        if (y > maxAltitude) return maxAltitude;
        return y;
    }
}
