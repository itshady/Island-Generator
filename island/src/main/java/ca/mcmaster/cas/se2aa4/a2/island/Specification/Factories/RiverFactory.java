package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.RiverGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

/**
 * Factory for river generation
 */

public class RiverFactory implements FeatureRunner {
    /**
     * Runs the river generator and inputs specified number of rivers.
     * @param island: Island to execute feature on
     * @param config: Number of rivers
     */
    @Override
    public void run(Island island, Configuration config) {
        try {
            String input = config.export(Configuration.RIVER);
            Integer numOfRivers;
            if (input != null) {
                numOfRivers = Integer.parseInt(input);
            } else {
                numOfRivers = 0;
            }
            RiverGenerator river = new RiverGenerator();
            river.process(island, numOfRivers);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
