package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

public class SeedFactory implements FeatureRunner {
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
