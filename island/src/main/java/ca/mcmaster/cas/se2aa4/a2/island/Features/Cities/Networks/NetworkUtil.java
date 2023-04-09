package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks;

import Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.List;

public abstract class NetworkUtil implements Network {
    protected VertexDecorator getMostCentralCity(Island island, List<VertexDecorator> cities) {
        VertexDecorator minCentroid = cities.get(0);
        double minDistance = distance(cities.get(0).getVertex().getCoordinate(), island.center());
        for (VertexDecorator city : cities) {
            double distance = distance(city.getVertex().getCoordinate(), island.center());
            if (distance < minDistance) {
                minCentroid = city;
                minDistance = distance;
            }
        }
        return minCentroid;
    }

    protected double distance(Coordinate coord1, Coordinate coord2) {
        double deltaX = coord2.getX() - coord1.getX();
        double deltaY = coord2.getY() - coord1.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
