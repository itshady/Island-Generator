import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.CoordinateList;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.*;

public class StructsToJTS {

    public org.locationtech.jts.geom.Polygon polygonToJTS(Structs.Polygon p, List<Structs.Vertex> vertices, List<Structs.Segment> segments) {
        GeometryFactory geometryFactory = new GeometryFactory();
        CoordinateList polygonCoordinates = new CoordinateList();
        for (Integer segmentId : p.getSegmentIdxsList()) {
            Structs.Segment currentSegment = segments.get(segmentId);
            Structs.Vertex v1 = vertices.get(currentSegment.getV1Idx());
            Structs.Vertex v2 = vertices.get(currentSegment.getV2Idx());
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
