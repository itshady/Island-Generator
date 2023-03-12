package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import EnhancedSets.PolygonSet;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;
import ca.mcmaster.cas.se2aa4.a2.island.StructsToJTS;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;
import Geometries.Polygon;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.awt.*;


public class Circle extends ShapeGenerator {
    Geometry circle;

    @Override
    protected boolean intersects(org.locationtech.jts.geom.Polygon JTSPolygon) {
        return JTSPolygon.intersects(circle);
    }

    @Override
    protected void initializeLand() {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        determineMeshCentre(gsf);
        gsf.setSize(350);
        gsf.setNumPoints(350);
        circle = gsf.createCircle();
    }
}
