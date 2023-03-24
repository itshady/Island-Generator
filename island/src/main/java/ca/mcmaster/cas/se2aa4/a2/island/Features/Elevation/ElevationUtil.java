package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

public abstract class ElevationUtil implements ElevationProfile {
    Coordinate center;
    Island island;
    public static Integer maxAltitude = 300;
    public static Integer minAltitude = 100;

    // Calculate altitude of a vertex on a land tile only!! (setAllAltitudes takes care of if its ocean or not)
    abstract Integer calculateAltitude(VertexDecorator vertex);

    public void process(Island container) {
        island = container;
        center = determineMeshCentre();

        setAllAltitudes();
    }

    private void setAllAltitudes() {
        for (Tile tile : island.getTiles()) {
            VertexDecorator centroid = tile.getCentroid();
            if (tile.isOcean()) centroid.setAltitude(minAltitude);
            else {
                Integer altitude = calculateAltitude(centroid);
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
        return island.center();
    }

    protected double getDistance(Coordinate v1, Coordinate v2) {
        double x1 = v1.getX();
        double y1 = v1.getY();
        double x2 = v2.getX();
        double y2 = v2.getY();

        return Math.sqrt(Math.pow((y2 - y1), 2) + Math.pow((x2 - x1), 2));
    }
}
