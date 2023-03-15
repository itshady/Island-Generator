package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Centroid;
import Geometries.Coordinate;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

public abstract class ElevationUtil implements ElevationProfile {
    Coordinate center;
    Island island;
    Integer maxAltitude = 300;
    Integer minAltitude = 100;

    // Calculate altitude of a vertex on a land tile only!! (setAllAltitudes takes care of if its ocean or not)
    abstract Integer calculateAltitude(Vertex vertex);

    public void process(Island container) {
        island = container;
        center = determineMeshCentre();

        setAllAltitudes();
    }

    private void setAllAltitudes() {
        for (Tile tile : island.getTiles()) {
            Centroid centroid = tile.getPolygon().getCentroid();
            if (tile.getType() == TileType.OCEAN) centroid.setAltitude(minAltitude);
            else {
                Integer altitude = calculateAltitude(centroid);
                centroid.setAltitude(altitude);
            }


            for (Segment segment : tile.getPolygon().getSegmentList()) {
                Vertex v1 = segment.getV1();
                Vertex v2 = segment.getV2();

                if (tile.getType() == TileType.OCEAN) {
                    v1.setAltitude(minAltitude);
                    v2.setAltitude(minAltitude);
                }
                else {
                    Integer altitude = calculateAltitude(v1);
                    v1.setAltitude(altitude);
                    altitude = calculateAltitude(v2);
                    v2.setAltitude(altitude);
                }
            }
        }
    }

    /**
     * Determines the centre point of the mesh.
     * Returns the centre coordinate (Used to determine triangle)
     */
    // THIS IS COPY PASTED FROM SHAPEGENERATOR: MIGHT WANNA ABSTRACT BUT IDK HOW
    protected Coordinate determineMeshCentre() {
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Vertex v: island.getVertices()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        Coordinate centre = new Coordinate(max_x/2, max_y/2);
        return centre;
    }

    protected double getDistance(Coordinate v1, Coordinate v2) {
        double x1 = v1.getX();
        double y1 = v1.getY();
        double x2 = v2.getX();
        double y2 = v2.getY();

        return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
    }
}
