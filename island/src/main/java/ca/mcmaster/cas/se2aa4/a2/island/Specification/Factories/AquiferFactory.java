package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.AquiferGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

/**
 * Factory for aquifer generation
 */

public class AquiferFactory implements FeatureRunner {
    /**
     * Runs the aquifer generator and inputs specified number of aquifer.
     * @param island: Island to execute feature on
     * @param config: Number of aquifer
     */
    @Override
    public void process(Island island, Configuration config) {
        try {
            String input = config.export(Configuration.AQUIFER);
            Integer numOfAquifers;
            if (input != null) {
                numOfAquifers = Integer.parseInt(input);
            } else {
                numOfAquifers = 0;
            }
            AquiferGenerator aquifer = new AquiferGenerator();
            aquifer.process(island, numOfAquifers);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
