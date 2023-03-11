package ca.mcmaster.cas.se2aa4.a2.island;

import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.GeometryFactory;


public class ADTtoJTSConverter {

    /**
     * Converts an ADT Polygon to a JTS Polygon
     * @param p: A Structs Polygon
     * @return : A JTS Polygon
     */


    public static org.locationtech.jts.geom.Polygon polygonToJTS(Polygon p) {
        GeometryFactory geometryFactory = new GeometryFactory();
        CoordinateList polygonCoordinates = new CoordinateList();
        for (Segment s : p.getSegmentList()) {
            Vertex v1 = s.getV1();
            Vertex v2 = s.getV2();
            Coordinate v1Coordinate = new Coordinate(v1.getX(), v1.getY());
            Coordinate v2Coordinate = new Coordinate(v2.getX(), v2.getY());
            polygonCoordinates.add(v1Coordinate, false);
            polygonCoordinates.add(v2Coordinate, false);
        }
        polygonCoordinates.add(polygonCoordinates.get(0), true);
        org.locationtech.jts.geom.Polygon poly = geometryFactory.createPolygon(polygonCoordinates.toCoordinateArray());
        return poly;
    }
}
