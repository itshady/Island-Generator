package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.RiverGenerator;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.Random;

public class RiverFactory implements FeatureRunner {
    @Override
    public void process(Island island, Configuration config) {
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
