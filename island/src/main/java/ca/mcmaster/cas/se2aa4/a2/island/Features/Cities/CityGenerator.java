package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities;

import Geometries.Coordinate;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Capitol;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.City;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Hamlet;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.City.Village;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks.NonStarNetwork;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks.StarNetwork;
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
            if (city.isCity() || tile.hasLake()) {
                i--;
                continue;
            }
            city.setCity(bindings.get(seed.nextInt(bindings.size())).clone());
            cities.add(city);
        }

        new NonStarNetwork().process(island, cities);
    }
}
