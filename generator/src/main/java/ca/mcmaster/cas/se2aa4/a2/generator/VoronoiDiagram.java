package ca.mcmaster.cas.se2aa4.a2.generator;

import org.locationtech.jts.geom.*;
import org.locationtech.jts.triangulate.VoronoiDiagramBuilder;

import java.util.ArrayList;
import java.util.List;

public class VoronoiDiagram {
    private Integer width;
    private Integer height;
    private final double precision = 0.01;

    public VoronoiDiagram(Integer width, Integer height) {
        this.width = width;
        this.height = height;
    }

    public List<Geometry> getVoronoiDiagram(List<Coordinate> coordsList) {
        return generateVoronoiDiagram(coordsList);
    }

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
            polygonsJTS.add(clippedDiagram.getGeometryN(i));
        }
        return polygonsJTS;
    }

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
