package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * NEEDS FIXING ONCE LAKE IS INTRODUCED, A LAGOON IS A SPECIALIZED SHAPE AND BIG LAKE
 */
public class Lagoon extends ShapeGenerator{

    Geometry land;
    Geometry lagoon;
    GeometricShapeFactory gsf = new GeometricShapeFactory();


    @Override
    protected void initializeLand() {
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setSize(350);
        gsf.setNumPoints(350);
        land = gsf.createCircle();
    }

    protected void initializeLagoon() {
        Coordinate meshCentre = determineMeshCentre();
        gsf.setCentre(meshCentre);
        gsf.setSize(200);
        gsf.setNumPoints(200);
        lagoon = gsf.createCircle();
    }

    @Override
    protected boolean intersects(Polygon JTSPolygon) {
        return intersects(JTSPolygon, land);
    }

    protected boolean intersects(Polygon JTSPolygon, Geometry geometry) {
        return JTSPolygon.intersects(geometry);
    }

    @Override
    public void process(Island container) {
        this.container = container;
        polygonReferences = container.getMappedPolygons();
        initializeLand();
        initializeLagoon();
        for (org.locationtech.jts.geom.Polygon JTSPolygon : polygonReferences.keySet()) {
            Geometries.Polygon ADTPolygon = polygonReferences.get(JTSPolygon);
            if (intersects(JTSPolygon, lagoon)) ADTPolygon.setColor(lagoonColor);
            else if (intersects(JTSPolygon)) ADTPolygon.setColor(landColor);
            else ADTPolygon.setColor(oceanColor);
        }
        determineBeachTiles();
    }

    private void determineBeachTiles() {
        List<Geometries.Polygon> polygonList = new ArrayList<>(polygonReferences.values());
        for (Geometries.Polygon p : polygonReferences.values()) {
            Color polygonColor = p.getColor();
            if (polygonColor == landColor) {
                for (Integer neighbourIdx : p.getPolygonNeighbours()) {
                    Geometries.Polygon currentNeighbour = polygonList.get(neighbourIdx);
                    Color neighbourPolygonColor = currentNeighbour.getColor();
                    if (neighbourPolygonColor == lagoonColor || neighbourPolygonColor == oceanColor) {
                        p.setColor(beachColor);
                        break;
                    }
                }
            }
        }
    }
}
