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


public class Circle extends ShapeGenerator implements Shape {

    GeometricShapeFactory gsf = new GeometricShapeFactory();
    Geometry circle;

    @Override
    public void process(ADTContainer container) {
        PolygonSet tiles = container.getPolygons();
        initialize(container);
        initializeLand();
        for (org.locationtech.jts.geom.Polygon JTSPolygon : polygonReferences.keySet()) {
            Polygon ADTPolygon = polygonReferences.get(JTSPolygon);
            if (JTSPolygon.intersects(circle)) {
                ADTPolygon.setColor(new Color(255,255,255,255));
            } else {
                ADTPolygon.setColor(new Color(0,87,143,255));
            }
        }

        // Map(JTSPolygon, ADTPolygon)
        // Intersects
    }

    @Override
    public void initializeLand() {
        determineMeshCentre(gsf);
        gsf.setSize(350);
        gsf.setNumPoints(350);
        circle = gsf.createCircle();
    }
}
