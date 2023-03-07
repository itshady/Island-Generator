package Helpers;

import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;

/**
 * A Helper to generate the Voronoi Diagram using JTS Library.
 */
public class VoronoiDiagram {
    private final Integer width;
    private final Integer height;
    private final double precision;

    public VoronoiDiagram(Integer width, Integer height, Double precision) {
        this.width = width;
        this.height = height;
        this.precision = precision;
    }

    public List<Geometry> getVoronoiDiagram(List<Coordinate> coordsList) {
        return generateVoronoiDiagram(coordsList);
    }

    /**
     * @param coordsList: a list of coordinates that will determine how the geometries are structured.
     * @return a list of geometries within the voronoi diagram.
     */

    private List<Geometry> generateVoronoiDiagram(List<Coordinate> coordsList) {
        // Create GeometryFactory to get voronoi diagram later
        GeometryFactory geometryFactory = new GeometryFactory();
        // Helps library create polygon by using MultiPoints
        MultiPoint points = geometryFactory.createMultiPointFromCoords(coordsList.toArray(new Coordinate[coordsList.size()]));
        // Create a boundary envelope
        Envelope envelope = new Envelope(0, width, 0, height);

        // Initialize voronoi diagram builder
        Geometry clippedDiagram = createVoronoiDiagram(geometryFactory, points, envelope);

        // Get all generated Polygons from the voronoi diagram builder
        List<Geometry> polygonsJTS = new ArrayList<>();
        for (int i=0; i<clippedDiagram.getNumGeometries(); i++) {
            ConvexHull convex = new ConvexHull(clippedDiagram.getGeometryN(i));
            polygonsJTS.add(convex.getConvexHull());
        }
        return polygonsJTS;
    }

    /**
     * Clips the voronoi diagram to the specified width and height.
     */
    private Geometry createVoronoiDiagram(GeometryFactory geometryFactory, MultiPoint points, Envelope envelope) {
        VoronoiDiagramBuilder voronoi = new VoronoiDiagramBuilder();
        voronoi.setTolerance(precision);
        voronoi.setSites(points); // Sets the vertices that will be diagrammed
        // creates the polygon vertices around generated sites
        Geometry diagram = voronoi.getDiagram(geometryFactory);

        // Clipped diagram to remove vertices outside height x width
        Geometry clippedDiagram = diagram.intersection(geometryFactory.toGeometry(envelope));
        return clippedDiagram;
    }
}
