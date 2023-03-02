import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.util.GeometricShapeFactory;

import java.util.*;
import java.util.List;

public class Lagoon {

    List<Structs.Vertex> vertices = new ArrayList<>();
    List<Structs.Segment> segments = new ArrayList<>();
    List<Structs.Polygon> polygons = new ArrayList<>();

    Geometry lagoonRadius;
    Geometry landRadius;
    TerrainPropertyHandler terrainPropertyHandler = new TerrainPropertyHandler();
    GeometricShapeFactory gsf = new GeometricShapeFactory();

    public Structs.Mesh generateLagoon(Structs.Mesh aMesh) {
        initializeLagoonRadius();
        initializeLandRadius();
        changeAllVertices(aMesh);
        changeAllSegments(aMesh);
        setPolygons(aMesh);
        determineBeachTiles();
        return Structs.Mesh.newBuilder().addAllVertices(vertices).addAllSegments(segments).addAllPolygons(polygons).build();
    }

    /**
     * Strips all colours away from the vertices of the input mesh.
     */
    private void changeAllVertices(Structs.Mesh aMesh) {
        for (Structs.Vertex v : aMesh.getVerticesList()) {
            double x = v.getX();
            double y = v.getY();
            boolean isCentroid = terrainPropertyHandler.isCentroid(v.getPropertiesList());
            Structs.Property centroid = Structs.Property.newBuilder().setKey("is_centroid").setValue(Boolean.toString(isCentroid)).build();
            Structs.Vertex blankVertex = Structs.Vertex.newBuilder().setX(x).setY(y).addProperties(centroid).build();
            vertices.add(blankVertex);
        }
    }

    /**
     * Strips all colours away from the segments of the input mesh.
     */
    private void changeAllSegments(Structs.Mesh aMesh) {
        for (Structs.Segment s : aMesh.getSegmentsList()) {
            int v1 = s.getV1Idx();
            int v2 = s.getV2Idx();
            Structs.Segment blankSegment = Structs.Segment.newBuilder().setV1Idx(v1).setV2Idx(v2).build();
            segments.add(blankSegment);
        }
    }

    /**
     * Creates the radius for the lagoon tiles.
     */
    private void initializeLagoonRadius() {
        gsf.setSize(200);
        gsf.setNumPoints(200);
        gsf.setCentre(new Coordinate(250, 250));
        lagoonRadius = gsf.createCircle();
    }

    /**
     * Creates the radius for the land tiles.
     */
    private void initializeLandRadius() {
        gsf.setSize(350);
        gsf.setNumPoints(300);
        gsf.setCentre(new Coordinate(250, 250));
        landRadius = gsf.createCircle();
    }

    private void setPolygons(Structs.Mesh aMesh) {
        Structs.Property tileProperty;
        Structs.Property colorProperty;
        for (Structs.Polygon p : aMesh.getPolygonsList()) {
            org.locationtech.jts.geom.Polygon poly = new StructsToJTS().polygonToJTS(p, vertices, segments);
            Structs.Polygon blankPolygon = Structs.Polygon.newBuilder().addAllSegmentIdxs(p.getSegmentIdxsList()).addAllNeighborIdxs(p.getNeighborIdxsList()).build();
            if (poly.intersects(lagoonRadius)) {
                tileProperty = terrainPropertyHandler.setTileProperty(TileType.LAGOON);
                colorProperty = terrainPropertyHandler.setColorProperty(TileType.LAGOON);
            } else if (poly.intersects(landRadius) && !poly.intersects(lagoonRadius)) {
                tileProperty = terrainPropertyHandler.setTileProperty(TileType.LAND);
                colorProperty = terrainPropertyHandler.setColorProperty(TileType.LAND);
            } else {
                tileProperty = terrainPropertyHandler.setTileProperty(TileType.OCEAN);
                colorProperty = terrainPropertyHandler.setColorProperty(TileType.OCEAN);
            }
            Structs.Polygon polygon = Structs.Polygon.newBuilder(blankPolygon).addProperties(colorProperty).addProperties(tileProperty).build();
            polygons.add(polygon);
        }
    }

    private void determineBeachTiles() {
        int counter = 0;
        for (Structs.Polygon p : polygons) {
            TileType currentPolygonTile = terrainPropertyHandler.extractTileProperty(p.getPropertiesList());
            if (currentPolygonTile == TileType.LAND) {
                for (Integer neighbourIdx : p.getNeighborIdxsList()) {
                    Structs.Polygon currentNeighbour = polygons.get(neighbourIdx);
                    TileType neighbourTileType = terrainPropertyHandler.extractTileProperty(currentNeighbour.getPropertiesList());
                    if (neighbourTileType == TileType.LAGOON || neighbourTileType == TileType.OCEAN) {
                        Structs.Property tileProperty = terrainPropertyHandler.setTileProperty(TileType.BEACH);
                        Structs.Property colorProperty = terrainPropertyHandler.setColorProperty(TileType.BEACH);
                        Structs.Polygon beachPolygon = Structs.Polygon.newBuilder(p).addProperties(colorProperty).addProperties(tileProperty).build();
                        polygons.set(counter, beachPolygon);
                        break;
                    }
                }
            }
            counter++;
        }
    }
}
