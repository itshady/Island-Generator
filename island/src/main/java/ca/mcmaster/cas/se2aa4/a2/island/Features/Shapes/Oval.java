package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import Geometries.Polygon;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;

import java.awt.*;

import EnhancedSets.PolygonSet;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;
import ca.mcmaster.cas.se2aa4.a2.island.StructsToJTS;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;
import Geometries.Polygon;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.util.AffineTransformation;
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
        determineMeshCentre(gsf);
        gsf.setHeight(400);
        gsf.setWidth(175);
        gsf.setNumPoints(350);
        gsf.setRotation(Math.toRadians(60));
        oval = gsf.createEllipse();
    }
}
