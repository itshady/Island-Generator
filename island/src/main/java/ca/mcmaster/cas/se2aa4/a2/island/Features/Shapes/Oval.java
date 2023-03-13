package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;


public class Oval extends ShapeGenerator {
    Geometry oval;

    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        return JTSPolygon.intersects(oval);
    }

    @Override
    protected void initializeLand() {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setHeight(400);
        gsf.setWidth(175);
        gsf.setNumPoints(350);
        gsf.setRotation(Math.toRadians(60));
        oval = gsf.createEllipse();
    }
}
