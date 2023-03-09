package Helpers;

import EnhancedSets.*;
import Geometries.*;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * StructsToGeneratorConvert converts the IO Library data structures
 * Into our Generator data structures (Polygon, Segment, Vertex)
 */

public class StructsToGeneratorConverter {

    PropertyHandler propertyHandler = new PropertyHandler();

    /**
     * Calls the method extractVertices and returns a Set compatible with the Generator
     * @param vertices is a List of type Vertex (Structs Package)
     * @return GeometrySet that contains the type Geometries.Vertex
     */
    public GeometrySet<Vertex> convertVertices(List<Structs.Vertex> vertices) {
        return extractVertices(vertices);
    }

    /**
     * Calls the method extractSegments and returns a Set compatible with the Generator
     * @param segments is a List of type Segment (Structs Package)
     * @param generatorVertices is a GeometrySet of type Vertex (Geometry Package)
     * @return GeometrySet that contains the type Geometries.Segment
     */
    public GeometrySet<Segment> convertSegments(List<Structs.Segment> segments, GeometrySet<Vertex> generatorVertices) {
        return extractSegments(segments, generatorVertices);
    }

    /**
     * Calls the method extractPolygons and returns a Set compatible with the Generator.
     * @param polygons is a List of type Polygon (Structs Package)
     * @param generatorSegments is a GeometrySet of type Segment (Geometry Package)
     * @param generatorVertices is a GeometrySet of type Vertex (Geometry Package)
     * @return GeometrySet that contains the type Geometries.Polygon
     */
    public GeometrySet<Polygon> convertPolygons(List<Structs.Polygon> polygons, GeometrySet<Segment> generatorSegments, GeometrySet<Vertex> generatorVertices) {
        PolygonSet convertedPolygons = extractPolygons(polygons, generatorSegments, generatorVertices);
        extractNeighbours(polygons, convertedPolygons);
        return convertedPolygons;
    }

    /**
     * Iterates through the Vertex List, converts the Structs.Vertex into Geometries.Vertex and returns a VertexSet
     * @param vertices is a List of type Vertex (Structs Package)
     * @return Set that contains the type Geometries.Vertex
     */
    private VertexSet extractVertices(List<Structs.Vertex> vertices) {
        VertexSet vertexSet = new VertexSet();
        for (Structs.Vertex vertex : vertices) {
            double vertexX = vertex.getX();
            double vertexY = vertex.getY();
            Color vertexColor = propertyHandler.extractColorProperty(vertex.getPropertiesList());
            Float vertexThickness = propertyHandler.extractThicknessProperty(vertex.getPropertiesList());
            boolean isCentroid = propertyHandler.isCentroid(vertex.getPropertiesList());
            if (isCentroid) {
                Centroid generatorVertex = new Centroid(vertexX, vertexY, vertexColor, vertexThickness);
                vertexSet.add(generatorVertex);
            } else {
                Vertex generatorVertex = new Vertex(vertexX, vertexY, vertexColor, vertexThickness);
                vertexSet.add(generatorVertex);
            }

        }
        return vertexSet;
    }

    /**
     * Iterates through the Segment List, converts the Structs.Segment into Geometries.Segment and returns a SegmentSet
     * @param segments is a List of type Segment (Structs Package)
     * @param generatorVertices is a GeometrySet of type Vertex
     * @return Set that contains the type Geometries.Segment
     */
    private SegmentSet extractSegments(List<Structs.Segment> segments, GeometrySet<Vertex> generatorVertices) {
        SegmentSet segmentSet = new SegmentSet();
        for (Structs.Segment segment : segments) {
            Vertex vertex1 = generatorVertices.get(segment.getV1Idx());
            Vertex vertex2 = generatorVertices.get(segment.getV2Idx());
            Color segmentColor = propertyHandler.extractColorProperty(segment.getPropertiesList());
            Float segmentThickness = propertyHandler.extractThicknessProperty(segment.getPropertiesList());
            Segment generatorSegment = new Segment(vertex1, vertex2, segmentColor, segmentThickness);
            segmentSet.add(generatorSegment);
        }
        return segmentSet;
    }

    /**
     * Iterates through the Polygon List, converts the Structs.Polygon into Geometries.Polygon and returns a PolygonSet
     * @param polygons is a List of type Polygon (Structs Package)
     * @param generatorSegments is a GeometrySet of type Segment
     * @param generatorVertices is a GeometrySet of type Vertex
     * @return Set that contains the type Geometries.Polygon
     */
    private PolygonSet extractPolygons(List<Structs.Polygon> polygons, GeometrySet<Segment> generatorSegments, GeometrySet<Vertex> generatorVertices) {
        PolygonSet polygonSet = new PolygonSet();
        for (Structs.Polygon polygon : polygons) {
            List<Integer> polygonSegmentIdxs = polygon.getSegmentIdxsList();
            List<Segment> polygonSegments = new ArrayList<>();
            for (Integer polygonSegmentIdx : polygonSegmentIdxs) {
                Segment currentSegment = generatorSegments.get(polygonSegmentIdx);
                polygonSegments.add(currentSegment);
            }
            Color polygonColor = propertyHandler.extractColorProperty(polygon.getPropertiesList());
            Float polygonThickness = propertyHandler.extractThicknessProperty(polygon.getPropertiesList());
            Polygon generatorPolygon = new Polygon(polygonSegments, polygonColor, polygonThickness);
            Centroid centroid = (Centroid) (generatorVertices.get(polygon.getCentroidIdx()));
            generatorPolygon.setCentroid(centroid);
            polygonSet.add(generatorPolygon);
        }
        return polygonSet;
    }

    /**
     * Iterates through the Structs.Polygon List and its neighbours
     * Finds the associated Geometry.Polygon neighbours and adds the list of neighbours to its Geometry.Polygon.
     * @param structsPolygons is a List of type Polygon (Structs Package)
     * @param generatorPolygons is a GeometrySet of type Polygon
     */
    private void extractNeighbours(List<Structs.Polygon> structsPolygons, GeometrySet<Polygon> generatorPolygons) {
        int counter = 0;
        for (Structs.Polygon structsPolygon : structsPolygons) {
            Polygon currentPolygon = generatorPolygons.get(counter);
            Set<Polygon> neighbours = new HashSet<>(structsPolygon.getNeighborIdxsCount());
            for (Integer neighbourIdx : structsPolygon.getNeighborIdxsList()) {
                Polygon neighbourPolygon = generatorPolygons.get(neighbourIdx);
                neighbours.add(neighbourPolygon);
            }
            currentPolygon.addPolygonNeighbourSet(neighbours);
            counter++;
        }
    }



}
