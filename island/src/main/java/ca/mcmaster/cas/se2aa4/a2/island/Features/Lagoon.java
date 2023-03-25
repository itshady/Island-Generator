package ca.mcmaster.cas.se2aa4.a2.island.Features;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.awt.Color;

/**
 * Sandbox island (MVP) for part 1. Does not utilize any other features.
 */
public class Lagoon {

    Geometry land;
    Geometry lagoon;
    Island island;
    GeometricShapeFactory gsf = new GeometricShapeFactory();


    /**
     * Initializes the land tiles based on desired size and number of points.
     */
    protected void initializeLand() {
        Coordinate meshCentre = new Coordinate(island.center().getX(), island.center().getY());
        gsf.setCentre(meshCentre);
        gsf.setSize(350);
        gsf.setNumPoints(350);
        land = gsf.createCircle();
    }

    /**
     * Initializes the lagoon tiles based on desired size and number of points.
     */
    protected void initializeLagoon() {
        Coordinate meshCentre = new Coordinate(island.center().getX(), island.center().getY());
        gsf.setCentre(meshCentre);
        gsf.setSize(200);
        gsf.setNumPoints(200);
        lagoon = gsf.createCircle();
    }

    /**
     * Checks the intersection of a polygon with land
     * @param JTSPolygon: Associated JTS polygon with our polygon
     * @return if polygon intersects with land radius
     */

    protected boolean intersects(Polygon JTSPolygon) {
        return intersects(JTSPolygon, land);
    }

    /**
     * Checks the intersection of a polygon with lagoon
     * @param JTSPolygon: Associated JTS polygon with our polygon
     * @return if polygon intersects with lagoon radius
     */

    protected boolean intersects(Polygon JTSPolygon, Geometry geometry) {
        return JTSPolygon.intersects(geometry);
    }

    /**
     * Enriches the tiles according to their position relative to the land and lagoon radii
     * @param container: island to enrich
     */
    public void process(Island container) {
        this.island = container;
        initializeLand();
        initializeLagoon();
        for (Tile tile : container.getTiles()) {
            if (intersects(tile.getJTSPolygon(), lagoon)) tile.setWater(new Lake());
            else if (!intersects(tile.getJTSPolygon())) tile.setWater(new Ocean());
        }
        determineBeachTiles();
    }

    /**
     * Determines which land tiles are classified as beach based on if their neighbours are water tiles
     */
    private void determineBeachTiles() {
        Color beach = new Color(242,243,200,255);
        Color land = Color.WHITE;
        for (Tile tile : island.getTiles()) {
            if (tile.isLand()) {
                tile.setColor(land);
                for (Integer neighbourIdx : tile.getNeighbours()) {
                    Tile currentNeighbour = island.getTiles().get(neighbourIdx);
                    if (currentNeighbour.hasLake() || currentNeighbour.isOcean()) {
                        tile.setColor(beach);
                        break;
                    }
                }
            }
        }
    }
}
