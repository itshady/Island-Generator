package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.geom.Coordinate;

import java.util.*;

public class NeighbourCalculator {

    public Map<Polygon, Set<Polygon>> getNeighbours(List<Geometry> polygonsJTS, Map<Integer, Polygon> polygons, List<Centroid> centroids) {
        return calculateNeighbours(polygonsJTS, polygons, centroids);
    }

    public void addNeighbours(List<Geometry> polygonsJTS, Map<Integer, Polygon> polygons, List<Centroid> centroids) {
        Map<Polygon, Set<Polygon>> neighbours = calculateNeighbours(polygonsJTS, polygons, centroids);
        addNeighboursToPolygons(neighbours);
    }

    private Map<Polygon, Set<Polygon>> calculateNeighbours(List<Geometry> polygonsJTS, Map<Integer, Polygon> polygons, List<Centroid> centroids) {
        Map<Polygon, Set<Polygon>> neighbours = new LinkedHashMap<>();
        List<Coordinate> c_coordList = new ArrayList<>();
        for (Centroid c : centroids) {
            Coordinate c_coord = new Coordinate(c.getX(), c.getY());
            c_coordList.add(c_coord);
        }

        GeometryFactory centroidFactory = new GeometryFactory();
        MultiPoint cPoints = centroidFactory.createMultiPointFromCoords(c_coordList.toArray(new Coordinate[polygonsJTS.size()]));
        DelaunayTriangulationBuilder delaunayTriangulation = new DelaunayTriangulationBuilder();
        delaunayTriangulation.setSites(cPoints);
        Geometry triangulation = delaunayTriangulation.getEdges(centroidFactory);

        for (int i = 0; i < polygonsJTS.size(); i++) {
            Geometry polygon = polygonsJTS.get(i);
            Polygon ourP1 = polygons.get(i);
            Coordinate centroid = polygon.getCentroid().getCoordinate();
            Set<Polygon> currentNeighbour = new HashSet<>();
            for (int j = 0; j < triangulation.getNumGeometries(); j++) {
                List<Coordinate> currentTriangleCoords = Arrays.stream(triangulation.getGeometryN(j).getCoordinates()).toList();
                if (currentTriangleCoords.contains(centroid)) {
                    for (Coordinate neighbourCentroid : currentTriangleCoords) {
                        Geometry p2 = polygonsJTS.get(polygonIndexFromCentroidCoord(neighbourCentroid, polygons));
                        Polygon ourP2 = polygons.get(polygonIndexFromCentroidCoord(neighbourCentroid, polygons));
                        if (!centroid.equals(neighbourCentroid) && (polygon.intersection(p2) instanceof LineString)) {
                            currentNeighbour.add(ourP2);
                        }
                    }
                }
            }
            neighbours.put(polygons.get(i), currentNeighbour);
        }

        return neighbours;
    }

    private void addNeighboursToPolygons(Map<Polygon, Set<Polygon>> neighbours) {
        for (Polygon p: neighbours.keySet()) {
            p.addPolygonNeighbourSet(neighbours.get(p));
            //System.out.println(p.getId() + "-" + p.getPolygonNeighbours());
        }
    }

    private Integer polygonIndexFromCentroidCoord(Coordinate centroidCoord, Map<Integer, Polygon> polygons) {
        for (Integer id : polygons.keySet()) {
            Coordinate currentCoord = new Coordinate(polygons.get(id).getCentroid().getX(), polygons.get(id).getCentroid().getY());
            if (currentCoord.equals(centroidCoord)) {
                return id;
            }
        }
        return -1;
    }
}
