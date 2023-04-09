package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.CityGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.LakeGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

public class CityFactory implements FeatureRunner {
    /**
     * Runs the City Generator and inputs specified number of cities.
     * @param island: Island to execute feature on
     * @param config: Configuration containing user input
     */
    @Override
    public void run(Island island, Configuration config) {
        try {
            String input = config.export(Configuration.CITIES);
            int numOfCities;
            if (input != null) {
                numOfCities = Integer.parseInt(input);
            } else {
                return;
            }
            if (numOfCities <= 0) return;
            CityGenerator city = new CityGenerator();
            city.process(island, numOfCities);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
