package ca.mcmaster.cas.se2aa4.a2.island;

import EnhancedSets.GeometrySet;
import Geometries.Polygon;
import Helpers.ADTToStructsConverterNew;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.StructsToADTConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

public class IslandBuilder {
    GeometrySet<Polygon> tiles;
    Island container;

    // Configuration Attributes
    Shape shape;

    public IslandBuilder(Structs.Mesh emptyMesh, Configuration configuration) { // pass in Configuration
        container = new StructsToADTConverter(emptyMesh).process();
        tiles = container.getPolygons();
        configure(configuration);
        process();
    }

    private void configure(Configuration configuration) {
        // feature = configuration.getFeature()
        shape = configuration.getShape();
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
