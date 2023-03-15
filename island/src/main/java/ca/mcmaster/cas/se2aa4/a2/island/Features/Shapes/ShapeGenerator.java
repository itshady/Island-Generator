package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;


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
     * @param container: A container with a set of polygons and vertices
     */
    public void process(Island container) {
        island = container;
        initializeLand();
        for (Tile tile : container.getTiles()) {
            if (intersects(tile.getJTSPolygon())) {
                tile.setType(TileType.LAND);
            }
            else {
                tile.setType(TileType.OCEAN);
            }
        }
    }

    /**
     * Determines the centre point of the mesh.
     * Returns the centre coordinate (Used to determine triangle)
     */
    protected Coordinate determineMeshCentre() {
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (VertexDecorator v: island.getVertexDecorators()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        Coordinate centre = new Coordinate(max_x/2, max_y/2);
        return centre;
    }
}
