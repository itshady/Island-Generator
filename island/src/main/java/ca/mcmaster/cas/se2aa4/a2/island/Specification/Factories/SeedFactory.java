package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

/**
 * Factory for seed reproducibility
 */
public class SeedFactory implements FeatureRunner {

    /**
     * Creates / executes the seed for island generation
     * @param island: Island to execute feature on
     * @param config: Chosen seed
     */

    @Override
    public void process(Island island, Configuration config) {
        try {
            Seed seed = Seed.getInstance(config.export(Configuration.SEED));
            seed.printSeed();
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
