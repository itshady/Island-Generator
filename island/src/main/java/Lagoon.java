import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Lagoon {

    List<Structs.Vertex> vertices = new ArrayList<>();
    List<Structs.Segment> segments = new ArrayList<>();
    Map<Integer, Structs.Polygon> polygons = new LinkedHashMap<>();
    private void changeAllVertices(Structs.Mesh aMesh) {
        for(Structs.Vertex v: aMesh.getVerticesList()){
            double x = v.getX();
            double y = v.getY();
            boolean isCentroid = isCentroid(v.getPropertiesList());
            Structs.Property centroid = Structs.Property.newBuilder().setKey("is_centroid").setValue(Boolean.toString(isCentroid)).build();
            Structs.Vertex blankVertex = Structs.Vertex.newBuilder().setX(x).setY(y).addProperties(centroid).build();
            vertices.add(blankVertex);
        }
    }

    private void changeAllSegments(Structs.Mesh aMesh) {
        for(Structs.Segment s: aMesh.getSegmentsList()){
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();
            Structs.Segment blankSegment = Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).build();
            segments.add(blankSegment);
        }
    }

    private boolean isCentroid(List<Structs.Property> properties) {
        String val = "false";
        for(Structs.Property p: properties) {
            if (p.getKey().equals("is_centroid")) {
                val = p.getValue();
            }
        }
        return val.equals("true");
    }

    private void setPolygons(Structs.Mesh aMesh) {
        GeometricShapeFactory gsf = new GeometricShapeFactory();
        GeometryFactory geometryFactory = new GeometryFactory();
        gsf.setSize(200);
        gsf.setNumPoints(200);
        gsf.setCentre(new Coordinate(250, 250));
        Geometry lagoon = gsf.createCircle();
        Color lagoonColor = new Color(103,168,209);
        Color landColor = new Color (255,255,255);
        Color oceanColor = new Color(0,87,143);
        gsf.setSize(350);
        gsf.setNumPoints(300);
        gsf.setCentre(new Coordinate(250, 250));
        Geometry land = gsf.createCircle();
        Integer currentId = 0;
        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            CoordinateList polygonCoordinates = new CoordinateList();
            structsToJTS(p, polygonCoordinates);
            org.locationtech.jts.geom.Polygon poly = geometryFactory.createPolygon(polygonCoordinates.toCoordinateArray());
            Structs.Property tileProperty;
            Structs.Property colorProperty;
            Structs.Polygon blankPolygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(p.getSegmentIdxsList()).addAllNeighborIdxs(p.getNeighborIdxsList()).build();
            if (poly.intersects(lagoon)) {
                tileProperty = setTileProperty(TileType.LAGOON);
                colorProperty = setColorProperty(lagoonColor);
            } else if (poly.intersects(land) && !poly.intersects(lagoon)) {
                tileProperty = setTileProperty(TileType.LAND);
                colorProperty = setColorProperty(landColor);
            } else {
                tileProperty = setTileProperty(TileType.OCEAN);
                colorProperty = setColorProperty(oceanColor);
            }
            Structs.Polygon polygon = Structs.Polygon.newBuilder(blankPolygon).addProperties(colorProperty).addProperties(tileProperty).build();
            polygons.put(currentId, polygon);
            currentId++;
        }
    }

//    private void beachTransformation() {
//        for (Structs.Polygon p : polygons.values()) {
//            //System.out.println(p.getTileType());
//            if (extractTileProperty(p.getPropertiesList()).equals()) {
//                for (Integer neighbour : p.getPolygonNeighbours()) {
//                    //System.out.println(polygonSet.get(neighbour).getTileType());
//                    TileType neighbourTileType = JTSconverter.getPolygons().get(neighbour).getTileType();
//                    if (neighbourTileType == TileType.OCEAN || neighbourTileType == TileType.LAGOON) {
//                        p.setTileType(TileType.BEACH);
//                        break;
//                    }
//                }
//            }
//        }
//    }

    private void structsToJTS(Structs.Polygon p, CoordinateList polygonCoordinates) {
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
    }

    public Structs.Mesh generateMesh(Structs.Mesh aMesh) {
        changeAllVertices(aMesh);
        changeAllSegments(aMesh);
        setPolygons(aMesh);

        return Structs.Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons.values().stream().toList()).build();
    }

    private Structs.Property setTileProperty(TileType tileType) {
        String tileProperty = tileType.toString();
        return Structs.Property.newBuilder().setKey("tile_property").setValue(tileProperty).build();
    }

    private Structs.Property setColorProperty(Color color) {
        String colorStr = ""+color.getRed()+","+color.getGreen()+","+color.getBlue()+","+color.getAlpha();
        return Structs.Property.newBuilder().setKey("rgba_color").setValue(colorStr).build();
    }

    private String extractTileProperty(List<Structs.Property> properties) {
        String val = null;
        for(Structs.Property p: properties) {
            if (p.getKey().equals("tile_property")) {
                val = p.getValue();
            }
        }
        if (val == null)
            return "OCEAN";
        return val;
    }

}
