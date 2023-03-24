package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.GeometryFactory;

/**
 * Maps a
 */
public class PolygonMapper {
    /**
     * Converts an ADT Polygon to a JTS Polygon
     * @param p: A Structs Polygon
     * @return : A JTS Polygon
     */
    public org.locationtech.jts.geom.Polygon process(Polygon p) {
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
        return geometryFactory.createPolygon(polygonCoordinates.toCoordinateArray());
    }
}
