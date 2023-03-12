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
import ca.mcmaster.cas.se2aa4.a2.island.Tile;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ShapeGenerator implements Shape {
    ADTContainer container;
    Map<Polygon, Geometries.Polygon> polygonReferences;

    Color oceanColor = new Color(0,87,143,255);
    Color lagoonColor = new Color(103,168,209,255);
    Color landColor = Color.WHITE;
    Color beachColor = new Color(242,243,200,255);

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
        polygonReferences = container.getMappedPolygons();
        initializeLand();
        for (org.locationtech.jts.geom.Polygon JTSPolygon : polygonReferences.keySet()) {
            Geometries.Polygon ADTPolygon = polygonReferences.get(JTSPolygon);
            Tile tile = new Tile(ADTPolygon.getSegmentList());
            if (intersects(JTSPolygon)) ADTPolygon.setColor(landColor);
            else ADTPolygon.setColor(oceanColor);
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
}
