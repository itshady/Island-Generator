package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.awt.Color;

/**
 * NEEDS FIXING ONCE LAKE IS INTRODUCED, A LAGOON IS A SPECIALIZED SHAPE AND BIG LAKE
 */
public class Lagoon extends ShapeGenerator{

    Geometry land;
    Geometry lagoon;
    GeometricShapeFactory gsf = new GeometricShapeFactory();


    @Override
    protected void initializeLand() {
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setSize(350);
        gsf.setNumPoints(350);
        land = gsf.createCircle();
    }

    protected void initializeLagoon() {
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setSize(200);
        gsf.setNumPoints(200);
        lagoon = gsf.createCircle();
    }

    @Override
    protected boolean intersects(Polygon JTSPolygon) {
        return intersects(JTSPolygon, land);
    }

    protected boolean intersects(Polygon JTSPolygon, Geometry geometry) {
        return JTSPolygon.intersects(geometry);
    }

    @Override
    public void process(Island container) {
        this.island = container;
        initializeLand();
        initializeLagoon();
        for (Tile tile : container.getTiles()) {
            if (intersects(tile.getJTSPolygon(), lagoon)) tile.setColor(lagoonColor);
            else if (intersects(tile.getJTSPolygon())) tile.setColor(landColor);
            else tile.setColor(oceanColor);
        }
        determineBeachTiles();
    }

    private void determineBeachTiles() {
        for (Tile tile : island.getTiles()) {
            Color polygonColor = tile.getColor();
            if (polygonColor == landColor) {
                for (Integer neighbourIdx : tile.getNeighbours()) {
                    Tile currentNeighbour = island.getTiles().get(neighbourIdx);
                    Color neighbourPolygonColor = currentNeighbour.getColor();
                    if (neighbourPolygonColor == lagoonColor || neighbourPolygonColor == oceanColor) {
                        tile.setColor(beachColor);
                        break;
                    }
                }
            }
        }
    }
}
