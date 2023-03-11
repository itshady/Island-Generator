package ca.mcmaster.cas.se2aa4.a2.island;

import EnhancedSets.GeometrySet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import Helpers.ADTToStructsConverterNew;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Shape;

public class Island {
    GeometrySet<Vertex> vertices;
    GeometrySet<Segment> segments;
    GeometrySet<Polygon> tiles;
    Shape shape;
    ADTContainer container;

    public Island(Structs.Mesh emptyMesh, Configuration configuration) { // pass in Configuration
        container = new StructsToADTConverter(emptyMesh).process();
        tiles = container.getPolygons();
        configure(configuration);
        process();
    }

    private void configure(Configuration configuration) {
        // feature = configuration.getFeature()
        shape = new Circle();
    }

    private void process() {
        shape.process(container);
    }

    public Structs.Mesh generate() {
        ADTToStructsConverterNew converter = new ADTToStructsConverterNew(container.getVertices(), container.getSegments(), tiles);
        return converter.process();
    }


    // Iterable Tiles
}
