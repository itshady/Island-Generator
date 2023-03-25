package ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters;

import EnhancedSets.PolygonSet;
import EnhancedSets.SegmentSet;
import EnhancedSets.VertexSet;
import Geometries.Segment;
import Geometries.Vertex;
import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.ArrayList;
import java.util.List;

public class IslandToMeshConverter {

    /**
     * Converts all VertexDecorators, Borders, and Tiles to MeshADT type equivalents
     * @param island, Island
     * @return Mesh, MeshADT type
     */
    public Mesh process(Island island) {
        Mesh mesh = new Mesh();
        mesh.vertices = convertToVertices(island.getVertexDecorators());
        mesh.segments = convertToSegments(island.getBorders());

        mesh.polygons = convertToPolygons(island.getTiles());
        return mesh;
    }

    /**
     * Collection of VertexDecorators are converted to MeshADT Vertices
     * @param decorators, List<VertexDecorator>
     * @return List<Vertex>
     */
    private VertexSet convertToVertices(List<VertexDecorator> decorators) {
        VertexSet vertices = new VertexSet();
        for (VertexDecorator decorator : decorators) {
            vertices.add(decorator.getVertex());
        }
        return vertices;
    }

    /**
     * Collection of Borders are converted to MeshADT Segments
     * @param borders, List<Border>
     * @return List<Segment>
     */
    private SegmentSet convertToSegments(List<Border> borders) {
        SegmentSet segments = new SegmentSet();
        for (Border border : borders) {
            segments.add(border.getSegment());
        }
        return segments;
    }

    /**
     * Collection of Tiles are converted to MeshADT Polygons
     * @param tiles, List<Tile>
     * @return PolygonSet -> EnhancedSet of a Polygon List to prevent duplicates
     */
    private PolygonSet convertToPolygons(List<Tile> tiles) {
        PolygonSet set = new PolygonSet();
        for (Tile tile : tiles) {
            set.add(tile.getPolygon());
        }
        return set;
    }
}
