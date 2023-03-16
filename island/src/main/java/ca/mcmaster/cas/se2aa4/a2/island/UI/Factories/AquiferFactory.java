package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Aquifer;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AquiferFactory implements FeatureRunner {

    @Override
    public void process(Island island, Configuration config) {
        try {
            String input = config.export(Configuration.AQUIFER);
            Integer numOfAquifers;
            if (input != null) {
                numOfAquifers = Integer.parseInt(input);
            } else {
                numOfAquifers = new Random().nextInt(0,7);
            }
            Aquifer aquifer = new Aquifer();
            aquifer.process(island, numOfAquifers);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
