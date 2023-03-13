package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.GeometricShapeFactory;

public class ThreeCircle extends ShapeGenerator {
    Geometry circle1;
    Geometry circle2;
    Geometry circle3;

    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        return JTSPolygon.intersects(circle1) || JTSPolygon.intersects(circle2) || JTSPolygon.intersects(circle3);
    }

    @Override
    protected void initializeLand() {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        Coordinate meshCentre = determineMeshCentre();
        Coordinate topLeft = new Coordinate(meshCentre.getX()*2/3, meshCentre.getY()*2/3);
        gsf.setCentre(topLeft);
        gsf.setSize(200);
        gsf.setNumPoints(350);
        circle1 = gsf.createCircle();

        Coordinate topRight = new Coordinate(meshCentre.getX()*1/3 + meshCentre.getX(), meshCentre.getY()*2/3);
        gsf.setCentre(topRight);
        gsf.setSize(200);
        gsf.setNumPoints(350);
        circle2 = gsf.createCircle();

        Coordinate bottomMiddle = new Coordinate(meshCentre.getX(), meshCentre.getY()*1/3 + meshCentre.getY());
        gsf.setCentre(bottomMiddle);
        gsf.setSize(200);
        gsf.setNumPoints(350);
        circle3 = gsf.createCircle();
    }
}
