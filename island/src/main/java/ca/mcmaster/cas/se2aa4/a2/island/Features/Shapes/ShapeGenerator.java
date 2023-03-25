package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Ocean;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;

/**
 * Contains methods that all Shapes require to be processed
 */
public abstract class ShapeGenerator implements Shape {
    Island island;

    /**
     * Note: intersects is abstracted so that irregular islands can be made.
     * If multiple shapes need to be concatenated, intersects can check them all.
     */

    /**
     * Initializes the shape of the island using JTS polygons
     */
    protected abstract void initializeLand();

    /**
     * Checks if a polygon is within the island shape.
     * @param JTSPolygon: A polygon to check intersects with the island shape
     * @return boolean: true if it intersects, else false
     */
    protected abstract boolean intersects(Polygon JTSPolygon);

    /**
     * Takes a set of polygons and add shape to it, distinguished by color
     * @param container: container with a set of tiles, borders, and VertexDecorators
     */
    public void process(Island container) {
        island = container;
        initializeLand();
        for (Tile tile : container.getTiles()) {
            if (intersects(tile.getJTSPolygon())) {
                tile.setColor(TileType.LAND.toColor());
            }
            else {
                tile.setWater(new Ocean());
            }
        }
    }

    /**
     * Determines the centre point of the mesh.
     * Returns the centre coordinate (Used to determine triangle)
     */
    protected Coordinate determineMeshCentre() {
        Geometries.Coordinate center = island.center();
        return new Coordinate(center.getX(), center.getY());
    }
}
