package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.util.ArrayList;
import java.util.List;

public class ThreeCircle extends ShapeGenerator {
    List<Geometry> shapes = new ArrayList<>();

    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        for (Geometry shape : shapes) {
            if (JTSPolygon.intersects(shape)) return true;
        }
        return false;
    }

    @Override
    protected void initializeLand() {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        Coordinate meshCentre = determineMeshCentre();
        Coordinate topLeft = new Coordinate(meshCentre.getX()*2/3, meshCentre.getY()*2/3);
        gsf.setCentre(topLeft);
        gsf.setSize(0.4 * Math.min(island.height(), island.width()));
        gsf.setNumPoints(350);
        shapes.add(gsf.createCircle());

        Coordinate topRight = new Coordinate(meshCentre.getX()*1/3 + meshCentre.getX(), meshCentre.getY()*2/3);
        gsf.setCentre(topRight);
        shapes.add(gsf.createCircle());

        Coordinate bottomMiddle = new Coordinate(meshCentre.getX(), meshCentre.getY()*1/3 + meshCentre.getY());
        gsf.setCentre(bottomMiddle);
        shapes.add(gsf.createCircle());
    }
}
