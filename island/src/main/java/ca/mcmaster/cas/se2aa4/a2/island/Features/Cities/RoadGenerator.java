package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities;

import Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.BorderBuilder;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.*;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.IdAlreadyExists;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.NoSuchNodeExists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoadGenerator {
    public List<List<Border>> getRoads(Island island, List<VertexDecorator> cities, VertexDecorator source) {
        List<VertexDecorator> centroidsOfInterest = new ArrayList<>();
        List<Border> bordersBetweenCentroids = new ArrayList<>();
        for (Tile tile : island.getLandTiles()) {
            VertexDecorator centroid = tile.getCentroid();
            centroidsOfInterest.add(centroid);
            // get all neighbour centroids that aren't in ocean
            List<VertexDecorator> neighbours = tile.getNeighbours().stream()
                    .map(island::getTile).filter(neighbor -> !neighbor.isOcean()).map(Tile::getCentroid).toList();

            for (VertexDecorator neighbour : neighbours) {
                bordersBetweenCentroids.add(Border.newBuilder()
                        .addV1(centroid).addV2(neighbour)
                        .addSegment(new Segment(centroid.getVertex(), neighbour.getVertex()))
                        .build());
            }
        }

        // add all nodes
        GraphADT graph = new GraphADT(false);
        for (int i=0; i<centroidsOfInterest.size(); i++) {
            try {
                graph.addNode(i);
            } catch (IdAlreadyExists e) {
                System.out.println(e.getMessage());
            }
        }

        // add all edges
        for (int i=0; i<bordersBetweenCentroids.size(); i++) {
            Border border = bordersBetweenCentroids.get(i);
            Integer startNode = centroidsOfInterest.indexOf(border.getV1());
            Integer endNode = centroidsOfInterest.indexOf(border.getV2());
            Double distance = 1.0;
            try {
                graph.addEdge(startNode, endNode, distance, i);
            } catch (IdAlreadyExists | NoSuchNodeExists e) {
                System.out.println(e.getMessage());
            }
        }

        Pathfinder pathfinder = new Dijkstra();
        Map<Node, List<Edge>> paths = pathfinder.getShortestPaths(graph, graph.getNode(centroidsOfInterest.indexOf(source)));

        // remove all paths that aren't part of the cities
        paths.entrySet().removeIf(entry -> {
            Node node = entry.getKey();
            VertexDecorator centroid = centroidsOfInterest.get(node.getId());
            return centroid == null || !cities.contains(centroid);
        });

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

        return result.values().stream().toList();
    }
}
