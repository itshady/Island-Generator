package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;


public class Oval extends ShapeGenerator {
    Geometry oval;

    /**
     * Checks whether a polygon intersects with the island for boundary purposes (seperate water and land)
     * @param JTSPolygon: A polygon to check intersects with the island shape
     * @return boolean
     */
    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        return JTSPolygon.intersects(oval);
    }

    /**
     * Generates an oval
     */
    @Override
    protected void initializeLand() {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setHeight(0.8 * island.height());
        gsf.setWidth(0.35 * island.width());
        gsf.setNumPoints(350);
        gsf.setRotation(Math.toRadians(60));
        oval = gsf.createEllipse();
    }
}
