package ca.mcmaster.cas.se2aa4.a2.generator.Helpers;


import Geometries.Centroid;
import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.GeometrySet;
import ca.mcmaster.cas.se2aa4.a2.generator.EnhancedSets.PolygonSet;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.DelaunayTriangulationBuilder;
import org.locationtech.jts.geom.Coordinate;
import Geometries.Polygon;

import java.util.*;

public class NeighbourCalculator {

    /**
     * Returns a set Map of a Polygon and a Set of its neighbours
     * @param polygonsJTS: A list of polygons from the JTS Library
     * @param polygons: A Set of our polygons
     * @param centroids: A list of centroids of the polygons
     * @return a Map of key Polygon and value a Set of its neighbours
     */
    public Map<Polygon, Set<Polygon>> getNeighbours(List<Geometry> polygonsJTS, GeometrySet<Polygon> polygons, List<Centroid> centroids) {
        return calculateNeighbours(polygonsJTS, polygons, centroids);
    }

    public void addNeighbours(List<Geometry> polygonsJTS, GeometrySet<Polygon> polygons, List<Centroid> centroids) {
        Map<Polygon, Set<Polygon>> neighbours = calculateNeighbours(polygonsJTS, polygons, centroids);
        addNeighboursToPolygons(neighbours);
    }

    /**
     * Iterates throughout each polygon and keeps track of its current centroid coordinate.
     * Finds all triangles with that centroid as a vertex and checks if those corresponding polygons share an edge.
     * If they share an edge, add that polygon to the neighbours list.
     */

    private Map<Polygon, Set<Polygon>> calculateNeighbours(List<Geometry> polygonsJTS, GeometrySet<Polygon> polygons, List<Centroid> centroids) {
        Map<Polygon, Set<Polygon>> neighbours = new LinkedHashMap<>();
        List<Coordinate> c_coordList = new ArrayList<>();

        // add each centroid's coordinates to a new list
        for (Centroid c : centroids) {
            Coordinate c_coord = new Coordinate(c.getX(), c.getY());
            c_coordList.add(c_coord);
        }

        // create new geometry factory for centroids and create delaunay's triangulation diagram
        GeometryFactory centroidFactory = new GeometryFactory();
        MultiPoint cPoints = centroidFactory.createMultiPointFromCoords(c_coordList.toArray(new Coordinate[polygonsJTS.size()]));
        DelaunayTriangulationBuilder delaunayTriangulation = new DelaunayTriangulationBuilder();
        delaunayTriangulation.setSites(cPoints);
        Geometry triangulation = delaunayTriangulation.getEdges(centroidFactory);

        for (int i = 0; i < polygonsJTS.size(); i++) {
            // iterate throughout each polygon and get its centroid coordinate
            Geometry polygon = polygonsJTS.get(i);
            Coordinate centroid = polygon.getCentroid().getCoordinate();
            Set<Polygon> currentNeighbour = new HashSet<>();
            for (int j = 0; j < triangulation.getNumGeometries(); j++) {
                // iterate throughout each triangle in Delaunay's Triangulation Diagram
                List<Coordinate> currentTriangleCoords = Arrays.stream(triangulation.getGeometryN(j).getCoordinates()).toList();
                if (currentTriangleCoords.contains(centroid)) {
                    // if a triangle contains the current centroid as a vertex, determine if they share an edge and add to neighbours
                    for (Coordinate neighbourCentroid : currentTriangleCoords) {
                        Geometry p2 = polygonsJTS.get(polygonIndexFromCentroidCoord(neighbourCentroid, polygons));
                        Polygon ourP2 = polygons.get(polygonIndexFromCentroidCoord(neighbourCentroid, polygons));
                        // if the intersection of the two polygons is a line, they share an edge
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
        }
    }

    /**
     * @param centroidCoord: A coordinate for a centroid from a polygon.
     * @param polygons: The set of polygons within the diagram.
     * @return the id of the corresponding polygon if found. If not, returns -1.
     */
    private Integer polygonIndexFromCentroidCoord(Coordinate centroidCoord, GeometrySet<Polygon> polygons) {
        PolygonSet polygonSet = (PolygonSet) polygons;
        for (Integer id : polygonSet.getPolygons().keySet()) {
            Coordinate currentCoord = new Coordinate(polygons.get(id).getCentroid().getX(), polygons.get(id).getCentroid().getY());
            if (currentCoord.equals(centroidCoord)) {
                return id;
            }
        }
        return -1;
    }
}
