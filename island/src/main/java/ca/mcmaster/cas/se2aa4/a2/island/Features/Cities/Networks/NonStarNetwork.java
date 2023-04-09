package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Capitol;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.City;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Hamlet;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Village;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Highway;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Secondary;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Road.Tertiary;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.RoadGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.*;
import java.util.stream.Collectors;

public class NonStarNetwork extends NetworkUtil {
    public void process(Island island, List<VertexDecorator> cities) {
        VertexDecorator centralHub = getMostCentralCity(island, cities);
        centralHub.setCity(new Capitol());

        // do all connections between capitols
        List<VertexDecorator> capitols = cities.stream()
                .filter(city -> city.getCity().isCapitol())
                .collect(Collectors.toList());
        List<List<Border>> roads = new ArrayList<>(new RoadGenerator().getRoads(island, capitols, centralHub));
        roads.forEach(road -> road.forEach(segment -> segment.setRoad(new Highway())));

        // do all connections between villages and nearby capitol
        for (VertexDecorator city : cities) {
            if (city.getCity().isVillage()) {
                List<VertexDecorator> capitol = getNearbyCities(cities, 1, List.of(Capitol.class), city);
                List<List<Border>> roadsToCapitol = new RoadGenerator().getRoads(island, capitol, city);
                roadsToCapitol.forEach(road -> road.forEach(segment -> segment.setRoad(new Secondary())));
                roads.addAll(roadsToCapitol);
            }
        }

        // do all connections between villages and nearby capitol
        for (VertexDecorator city : cities) {
            if (city.getCity().isHamlet()) {
                List<VertexDecorator> hamletsAndVillages = getNearbyCities(cities, 2, List.of(Hamlet.class, Village.class), city);
                List<List<Border>> roadsToHamletsAndVillages = new RoadGenerator().getRoads(island, hamletsAndVillages, city);
                roadsToHamletsAndVillages.forEach(road -> road.forEach(segment -> segment.setRoad(new Tertiary())));
                roads.addAll(roadsToHamletsAndVillages);
            }
        }


        List<Border> currentBorders = island.getBorders();
        roads.forEach(currentBorders::addAll);
        island.register(island.getVertexDecorators(), currentBorders, island.getTiles());
    }

    private List<VertexDecorator> getNearbyCities(List<VertexDecorator> cities, Integer numOfCity, List<Class<? extends City>> klass, VertexDecorator source) {
        Map<VertexDecorator, Double> distancesFromSource = new HashMap<>();
        for (VertexDecorator city : cities) {
            if (city != source && klass.contains(city.getCity().getClass())) {
                distancesFromSource.put(city, distance(source.getVertex().getCoordinate(), city.getVertex().getCoordinate()));
            }
        }

        // gets the smallest x cities from the list
        return distancesFromSource.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(numOfCity)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
