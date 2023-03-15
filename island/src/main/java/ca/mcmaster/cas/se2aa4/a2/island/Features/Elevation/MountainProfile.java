package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Centroid;
import Geometries.Coordinate;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

public class MountainProfile extends ElevationUtil {

    protected Integer calculateAltitude(Vertex vertex) {
        Double distanceFromCenter = getDistance(center,vertex.getCoordinate());
        Integer slope = -1;
        Integer y = maxAltitude + (int)(distanceFromCenter*slope);
        if (y < minAltitude) return minAltitude;
        return y;
    }
}
