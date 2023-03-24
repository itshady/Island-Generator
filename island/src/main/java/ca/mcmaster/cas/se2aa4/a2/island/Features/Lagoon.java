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
 * NEEDS FIXING ONCE LAKE IS INTRODUCED, A LAGOON IS A SPECIALIZED SHAPE AND BIG LAKE
 */
public class Lagoon {

    Geometry land;
    Geometry lagoon;
    Island island;
    GeometricShapeFactory gsf = new GeometricShapeFactory();


    protected void initializeLand() {
        Coordinate meshCentre = new Coordinate(island.center().getX(), island.center().getY());
        gsf.setCentre(meshCentre);
        gsf.setSize(350);
        gsf.setNumPoints(350);
        land = gsf.createCircle();
    }

    protected void initializeLagoon() {
        Coordinate meshCentre = new Coordinate(island.center().getX(), island.center().getY());
        gsf.setCentre(meshCentre);
        gsf.setSize(200);
        gsf.setNumPoints(200);
        lagoon = gsf.createCircle();
    }

    protected boolean intersects(Polygon JTSPolygon) {
        return intersects(JTSPolygon, land);
    }

    protected boolean intersects(Polygon JTSPolygon, Geometry geometry) {
        return JTSPolygon.intersects(geometry);
    }

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
