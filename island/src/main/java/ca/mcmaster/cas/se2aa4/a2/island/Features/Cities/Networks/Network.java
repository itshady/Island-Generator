package ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks;

import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.List;

public interface Network {
    /**
     * Given a list of cities, add the cities and roads to the island.
     * @param island
     * @param cities
     */
    void process(Island island, List<VertexDecorator> cities);
}
