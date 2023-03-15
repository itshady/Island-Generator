package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;
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
            if (intersects(tile.getJTSPolygon(), lagoon)) tile.setType(TileType.LAKE);
            else if (intersects(tile.getJTSPolygon())) tile.setType(TileType.LAND);
            else tile.setType(TileType.OCEAN);
        }
        determineBeachTiles();
    }

    private void determineBeachTiles() {
        for (Tile tile : island.getTiles()) {
            if (tile.getType() == TileType.LAND) {
                for (Integer neighbourIdx : tile.getNeighbours()) {
                    Tile currentNeighbour = island.getTiles().get(neighbourIdx);
                    if (currentNeighbour.getType() == TileType.LAKE || currentNeighbour.getType() == TileType.OCEAN) {
                        tile.setType(TileType.BEACH);
                        break;
                    }
                }
            }
        }
    }
}
