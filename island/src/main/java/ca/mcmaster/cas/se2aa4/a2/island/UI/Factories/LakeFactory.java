package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.LakeGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.Random;

public class LakeFactory implements FeatureRunner {
    @Override
    public void process(Island island, Configuration config) {
        try {
            String input = config.export(Configuration.LAKE);
            Integer numOfLakes;
            if (input != null) {
                numOfLakes = Integer.parseInt(input);
            } else {
                numOfLakes = new Random().nextInt(0,5);
            }
            LakeGenerator lake = new LakeGenerator();
            lake.process(island, numOfLakes);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
