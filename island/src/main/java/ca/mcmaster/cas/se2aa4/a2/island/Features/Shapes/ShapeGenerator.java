package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Centroid;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.island.ADTtoJTSConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class ShapeGenerator implements Shape {
    ADTContainer container;
    Map<Polygon, Geometries.Polygon> polygonReferences = new HashMap<>();

    /**
     * Initializes the shape of the island using JTS polygons
     */
    protected abstract void initializeLand();

    /**
     * Checks if a polygon is within the island shape.
     * @param JTSPolygon: A polygon to check intersects with the island shape
     * @return boolean: true if it intersects, else false
     */
    protected abstract boolean intersects(Polygon JTSPolygon);

    /**
     * Takes a set of polygons and add shape to it, distinguished by color
     * @param container: A container with a set of polygons and vertices
     */
    public void process(ADTContainer container) {
        this.container = container;
        mapPolygons();
        initializeLand();
        for (org.locationtech.jts.geom.Polygon JTSPolygon : polygonReferences.keySet()) {
            Geometries.Polygon ADTPolygon = polygonReferences.get(JTSPolygon);
            if (intersects(JTSPolygon)) ADTPolygon.setColor(new Color(255,255,255,255));
            else ADTPolygon.setColor(new Color(0,87,143,255));
        }
    }

    /**
     * Determines the centre point of the mesh.
     */
    protected void determineMeshCentre(GeometricShapeFactory gsf) {
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Vertex v: container.getVertices()) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        Coordinate centre = new Coordinate(max_x/2, max_y/2);
        gsf.setCentre(centre);
    }

    /**
     * Maps the JTSPolygon to its ADT version
     */
    protected void mapPolygons() {
        for (Geometries.Polygon p : container.getPolygons()) {
            Polygon JTSPolygon = ADTtoJTSConverter.polygonToJTS(p);
            polygonReferences.put(JTSPolygon, p);
        }
    }
}
