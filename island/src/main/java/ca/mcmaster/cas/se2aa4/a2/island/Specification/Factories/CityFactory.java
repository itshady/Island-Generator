package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.CityGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks.Network;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks.NetworkUtil;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks.NonStarNetwork;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Cities.Networks.StarNetwork;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

import java.util.HashMap;
import java.util.Map;

public class CityFactory implements FeatureRunner {
    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    // Puts all the necessary soil types into the bindings map
    static {
        bindings.put("star", StarNetwork.class);
        bindings.put("nonstar", NonStarNetwork.class);
        bindings.put(DEFAULT, StarNetwork.class);
    }

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

            String networkInput = config.export(Configuration.NETWORK);
            networkInput = networkInput != null ? networkInput.toLowerCase() : null;
            Class networkClass = bindings.getOrDefault(networkInput, StarNetwork.class);
            Network network = ((Network) networkClass.getDeclaredConstructor().newInstance());
            CityGenerator city = new CityGenerator();
            city.process(island, numOfCities, network);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
