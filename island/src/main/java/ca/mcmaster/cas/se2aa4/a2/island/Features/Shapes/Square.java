package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;


public class Square extends ShapeGenerator {
    Geometry square;

    /**
     * Checks whether a polygon intersects with the island for boundary purposes (seperate water and land)
     * @param JTSPolygon: A polygon to check intersects with the island shape
     * @return boolean
     */
    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        return JTSPolygon.intersects(square);
    }

    /**
     * Generates a square
     */
    @Override
    protected void initializeLand() {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setSize(0.7 * (Math.min(island.height(), island.width())));
        gsf.setNumPoints(350);
        square = gsf.createRectangle();
    }
}
