package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Capitol;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Highway;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.List;

/**
 * StarNetwork is a way to connect all cities (capitols, villages, hamlets).
 * The most central city connects to all other cities via highways.
 * In other words, all cities go through the central one.
 */
public class StarNetwork extends NetworkUtil {
    public void process(Island island, List<VertexDecorator> cities) {
        VertexDecorator centralHub = getMostCentralCity(island, cities);
        centralHub.setCity(new Capitol());
        List<List<Border>> roads = new RoadGenerator().getRoads(island, cities, centralHub);
        roads.forEach(road -> road.forEach(segment -> segment.setRoad(new Highway())));
        List<Border> currentBorders = island.getBorders();
        roads.forEach(currentBorders::addAll);
        island.register(island.getVertexDecorators(), currentBorders, island.getTiles());
    }
}
