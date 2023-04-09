package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities;

import Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.IdAlreadyExists;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.Exceptions.NoSuchNodeExists;
import ca.mcmaster.cas.se2aa4.a4.pathfinder.GraphADT;

import java.util.List;

public class IslandToGraphAdapter implements GraphAdapter<VertexDecorator, Border> {
    /**
     * Translates the incoming vertices and borders to graph representation
     * @param vertices
     * @param borders
     * @return GraphADT holding the node and borders
     */
    public GraphADT translate(List<VertexDecorator> vertices, List<Border> borders) {
        // add all nodes
        GraphADT graph = new GraphADT(false);
        for (int i = 0; i< vertices.size(); i++) {
            try {
                graph.addNode(i);
            } catch (IdAlreadyExists e) {
                System.out.println(e.getMessage());
            }
        }

        // add all edges
        for (int i = 0; i< borders.size(); i++) {
            Border border = borders.get(i);
            Integer startNode = vertices.indexOf(border.getV1());
            Integer endNode = vertices.indexOf(border.getV2());
            Double distance = distance(border.getV1().getVertex().getCoordinate(), border.getV2().getVertex().getCoordinate());
            try {
                graph.addEdge(startNode, endNode, distance, i);
            } catch (IdAlreadyExists | NoSuchNodeExists e) {
                System.out.println(e.getMessage());
            }
        }
        return graph;
    }

    /**
     * Gets euclidean distance between two coordinates
     * @param coord1
     * @param coord2
     * @return double of distance
     */
    private double distance(Coordinate coord1, Coordinate coord2) {
        double deltaX = coord2.getX() - coord1.getX();
        double deltaY = coord2.getY() - coord1.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
