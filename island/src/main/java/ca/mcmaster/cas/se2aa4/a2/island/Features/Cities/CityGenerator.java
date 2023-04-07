package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities;

import Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Capitol;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.City;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Hamlet;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Village;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Highway;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Road;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Secondary;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityGenerator {
    private static final Map<Integer, City> bindings = new HashMap();

    // Puts all the necessary features into the bindings map
    static {
        bindings.put(0, new Capitol());
        bindings.put(1, new Village());
        bindings.put(2, new Hamlet());
    }

    public void process(Island island, Integer numOfCities) {
        Seed seed = Seed.getInstance();
        List<Tile> landTiles = island.getLandTiles();
        List<VertexDecorator> cities = new ArrayList<>();
        for (int i=0; i<numOfCities; i++) {
            Tile tile = landTiles.get(seed.nextInt(landTiles.size()));
            VertexDecorator city = tile.getCentroid();
            if (city.isCity()) {
                i--;
                continue;
            }
            city.setCity(bindings.get(seed.nextInt(bindings.size())).clone());
            cities.add(city);
        }

        VertexDecorator centralHub = getMostCentralCity(island, cities);
        centralHub.setCity(new Capitol());
        List<List<Border>> roads = new RoadGenerator().getRoads(island, cities, centralHub);
        roads.forEach(road -> road.forEach(segment -> segment.setRoad(new Highway())));
        List<Border> currentBorders = island.getBorders();
        roads.forEach(currentBorders::addAll);
        island.register(island.getVertexDecorators(), currentBorders, island.getTiles());
    }

    private VertexDecorator getMostCentralCity(Island island, List<VertexDecorator> cities) {
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

    public double distance(Coordinate coord1, Coordinate coord2) {
        double deltaX = coord2.getX() - coord1.getX();
        double deltaY = coord2.getY() - coord1.getY();
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
}
