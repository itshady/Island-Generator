package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities;

import Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadGenerator {
    /**
     * Given cities and the central city, get a list of paths from the central city to all the rest.
     * @param island
     * @param cities
     * @param source
     * @return List of paths in the form of borders
     */
    public List<List<Border>> getRoads(Island island, List<VertexDecorator> cities, VertexDecorator source) {
        List<VertexDecorator> centroidsOfInterest = new ArrayList<>();
        List<Border> bordersBetweenCentroids = new ArrayList<>();
        for (Tile tile : island.getLandTiles()) {
            if (tile.hasLake()) continue;
            VertexDecorator centroid = tile.getCentroid();
            centroidsOfInterest.add(centroid);
            // get all neighbour centroids that aren't ocean or lake
            List<VertexDecorator> neighbours = tile.getNeighbours().stream()
                    .map(island::getTile).filter(neighbor -> !neighbor.isOcean() && !neighbor.hasLake())
                    .map(Tile::getCentroid).toList();

            for (VertexDecorator neighbour : neighbours) {
                bordersBetweenCentroids.add(Border.newBuilder()
                        .addV1(centroid).addV2(neighbour)
                        .addSegment(new Segment(centroid.getVertex(), neighbour.getVertex()))
                        .build());
            }
        }

        GraphAdapter<VertexDecorator, Border> adapter = new IslandToGraphAdapter();
        GraphADT graph = adapter.translate(centroidsOfInterest, bordersBetweenCentroids);

        Pathfinder pathfinder = new Dijkstra();
        Map<Node, List<Edge>> paths = pathfinder.getShortestPaths(graph, graph.getNode(centroidsOfInterest.indexOf(source)));

        paths = getUsefulPaths(cities, centroidsOfInterest, paths);

        Map<VertexDecorator, List<Border>> result = translatePathsToIsland(centroidsOfInterest, bordersBetweenCentroids, paths);

        return result.values().stream().toList();
    }

    /**
     * Remove all paths that aren't part of the cities
     * @param cities
     * @param allCentroids
     * @param paths
     * @return Map with the important nodes and paths to them
     */
    private Map<Node, List<Edge>> getUsefulPaths(List<VertexDecorator> cities, List<VertexDecorator> allCentroids, Map<Node, List<Edge>> paths) {
        Map<Node, List<Edge>> pathsCopy = new HashMap<>(paths);
        pathsCopy.entrySet().removeIf(entry -> {
            Node node = entry.getKey();
            VertexDecorator centroid = allCentroids.get(node.getId());
            return centroid == null || !cities.contains(centroid);
        });
        return pathsCopy;
    }

    /**
     * Translates the graph paths back to island geometries.
     * Node -> VertexDecorator, Edge -> Border
     * @param centroidsOfInterest
     * @param bordersBetweenCentroids
     * @param paths
     * @return Map holding path to a VertexDecorator from the central city
     */
    private Map<VertexDecorator, List<Border>> translatePathsToIsland(List<VertexDecorator> centroidsOfInterest, List<Border> bordersBetweenCentroids, Map<Node, List<Edge>> paths) {
        Map<VertexDecorator, List<Border>> result = new HashMap<>();
        for (Map.Entry<Node, List<Edge>> entry : paths.entrySet()) {
            Node node = entry.getKey();
            List<Edge> edges = entry.getValue();
            VertexDecorator centroid = centroidsOfInterest.get(node.getId());
            List<Border> borders = new ArrayList<>();
            for (Edge edge : edges) {
                int borderId = edge.getId();
                Border border = bordersBetweenCentroids.get(borderId);
                borders.add(border);
            }
            result.put(centroid, borders);
        }
        return result;
    }
}
