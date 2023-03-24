package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.LakeGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

public class LakeFactory implements FeatureRunner {
    @Override
    public void process(Island island, Configuration config) {
        try {
            String input = config.export(Configuration.LAKE);
            Integer numOfLakes;
            if (input != null) {
                numOfLakes = Integer.parseInt(input);
            } else {
                numOfLakes = 0;
            }
            LakeGenerator lake = new LakeGenerator();
            lake.process(island, numOfLakes);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
