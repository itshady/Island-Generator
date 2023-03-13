package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.util.Arrays;
import java.util.Random;

public class Triangle extends ShapeGenerator{

    Geometry triangle;

    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        return JTSPolygon.intersects(triangle);
    }

    @Override
    protected void initializeLand() {
        GeometryFactory gf = new GeometryFactory();
        Coordinate centroid = determineMeshCentre();

        // Set the base and height of the triangle
        double base = 200;
        double height = 400;

        // Calculate the coordinates of the vertices
        Coordinate[] vertices = new Coordinate[] {
                new Coordinate(centroid.getX() - base/2, centroid.getY() - height/3),
                new Coordinate(centroid.getX() + base/2, centroid.getY() - height/3),
                new Coordinate(centroid.getX(), centroid.getY() + 2*height/3),
                new Coordinate(centroid.getX() - base/2, centroid.getY() - height/3)
        };

        // Create a LinearRing representing the triangle's exterior boundary
        LinearRing shell = gf.createLinearRing(vertices);
        triangle = gf.createPolygon(shell);
    }

}
