package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Aquifer;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.HashMap;
import java.util.Map;

public class AquiferFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("aquifer", Aquifer.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            Class aquiferClass = bindings.getOrDefault(config.export(Configuration.AQUIFER), Aquifer.class);
            Aquifer aquifer = ((Aquifer) aquiferClass.getDeclaredConstructor().newInstance());
            aquifer.process(island, Integer.parseInt(config.export(Configuration.AQUIFER)));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
