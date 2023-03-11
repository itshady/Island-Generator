package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

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

public abstract class ShapeGenerator {
    VertexSet vertices;
    SegmentSet segments;
    PolygonSet polygons;

    Map<Polygon, Geometries.Polygon> polygonReferences = new HashMap<>();



    /**
     * Determines the centre point of the mesh.
     */
    protected void determineMeshCentre(GeometricShapeFactory gsf) {
        double max_x = Double.MIN_VALUE;
        double max_y = Double.MIN_VALUE;
        for (Vertex v: vertices) {
            max_x = (Double.compare(max_x, v.getX()) < 0? v.getX(): max_x);
            max_y = (Double.compare(max_y, v.getY()) < 0? v.getY(): max_y);
        }
        Coordinate centre = new Coordinate(max_x/2, max_y/2);
        gsf.setCentre(centre);
    }

    /**
     * Strips all colours away from the vertices of the input mesh.
     */
    protected void changeAllVertices() {
        for (Vertex v : vertices) {
            double x = v.getX();
            double y = v.getY();

            if (v.isCentroid()) {
                Centroid newVertex = new Centroid(x, y, Color.BLACK, 0f);
                vertices.update(v, newVertex);
            } else {
                Vertex newVertex = new Vertex(x, y, Color.BLACK,0f);
                vertices.update(v, newVertex);
            }
        }
    }

    /**
     * Strips all colours away from the segments of the input mesh.
     */
    protected void changeAllSegments() {
        for (Segment s : segments) {
            Vertex v1 = s.getV1();
            Vertex v2 = s.getV2();
            Segment newSegment = new Segment(v1, v2, Color.BLACK, 0f);
            segments.update(s, newSegment);
        }
    }

    protected void initialize(ADTContainer container) {
        vertices = container.getVertices();
        segments = container.getSegments();
        polygons = container.getPolygons();
        changeAllVertices();
        changeAllSegments();
        mapPolygons();

    }

    protected void mapPolygons() {
        for (Geometries.Polygon p : polygons) {
            Polygon JTSPolygon = ADTtoJTSConverter.polygonToJTS(p);
            polygonReferences.put(JTSPolygon, p);
        }
    }


}
