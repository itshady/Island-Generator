package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

/**
 * Contains methods that all profiles for elevation requires
 */
public abstract class ElevationUtil implements ElevationProfile {
    Coordinate center;
    Island island;
    public static Double maxAltitude = 200.0;
    public static Double minAltitude = 0.0;

    // Calculate altitude of a vertex on a land tile only (setAllAltitudes takes care of if its ocean or not)
    // Every profile will need this method, except the implementation will be different
    abstract Double calculateAltitude(VertexDecorator vertex);

    /**
     * Gets the centre of the mesh and sets altitudes based on the profile
     * @param container, Island
     */
    public void process(Island container) {
        island = container;
        center = island.center();

        setAllAltitudes();
    }

    /**
     * Iterates through each tile and sets corresponding altitude values
     */
    private void setAllAltitudes() {
        for (Tile tile : island.getTiles()) {
            VertexDecorator centroid = tile.getCentroid();
            if (tile.isOcean()) centroid.setAltitude(minAltitude);
            else {
                Double altitude = calculateAltitude(centroid);
                centroid.setAltitude(altitude);
            }


            for (Border border : tile.getBorders()) {
                VertexDecorator v1 = border.getV1();
                VertexDecorator v2 = border.getV2();

                if (tile.isOcean()) {
                    v1.setAltitude(minAltitude);
                    v2.setAltitude(minAltitude);
                }
                else {
                    Double altitude = calculateAltitude(v1);
                    v1.setAltitude(altitude);
                    altitude = calculateAltitude(v2);
                    v2.setAltitude(altitude);
                }
            }
        }
    }

    protected double getDistance(Coordinate v1, Coordinate v2) {
        double x1 = v1.getX();
        double y1 = v1.getY();
        double x2 = v2.getX();
        double y2 = v2.getY();

        return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
    }
}
