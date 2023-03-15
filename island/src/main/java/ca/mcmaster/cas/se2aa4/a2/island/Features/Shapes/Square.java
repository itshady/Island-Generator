package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;


public class Square extends ShapeGenerator {
    Geometry square;

    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        return JTSPolygon.intersects(square);
    }

    @Override
    protected void initializeLand() {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setSize(350);
        gsf.setNumPoints(350);
        square = gsf.createRectangle();
    }
}
