package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Capitol;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Highway;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.RoadGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.List;

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
