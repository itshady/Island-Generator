package ca.mcmaster.cas.se2aa4.a2.island;

import EnhancedSets.GeometrySet;
import EnhancedSets.PolygonSet;
import EnhancedSets.VertexSet;
import Geometries.Polygon;
import Geometries.Segment;
import Geometries.Vertex;
import Helpers.ADTToStructsConverter;
import Helpers.ADTToStructsConverterNew;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;

public class Island {
    GeometrySet<Vertex> vertices;
    GeometrySet<Segment> segments;
    GeometrySet<Polygon> tiles;

    public Island(Structs.Mesh emptyMesh) {
        ADTContainer container = new StructsToADTConverter(emptyMesh).process();
        tiles = container.getPolygons();
        segments = container.getSegments();
        vertices = container.getVertices();

    }

    public Structs.Mesh generate() {
        ADTToStructsConverterNew converter = new ADTToStructsConverterNew(vertices, segments, tiles);
        return converter.process();
    }


    // Iterable Tiles
}
