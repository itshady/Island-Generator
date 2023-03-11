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


public class Oval extends ShapeGenerator implements Shape{
    GeometricShapeFactory gsf = new GeometricShapeFactory();
    Geometry oval;
    @Override
    public void process(ADTContainer container) {
        initialize(container);
        initializeLand();
        for (org.locationtech.jts.geom.Polygon JTSPolygon : polygonReferences.keySet()) {
            Polygon ADTPolygon = polygonReferences.get(JTSPolygon);
            if (JTSPolygon.intersects(oval)) {
                ADTPolygon.setColor(new Color(255,255,255,255));
            } else {
                ADTPolygon.setColor(new Color(0,87,143,255));
            }
        }

        // Map(JTSPolygon, ADTPolygon)
        // Intersects
    }

    protected void initializeLand() {
        determineMeshCentre(gsf);
        gsf.setHeight(400);
        gsf.setWidth(175);
        gsf.setNumPoints(350);
        gsf.setRotation(Math.toRadians(60));
        oval = gsf.createEllipse();

    }
}
