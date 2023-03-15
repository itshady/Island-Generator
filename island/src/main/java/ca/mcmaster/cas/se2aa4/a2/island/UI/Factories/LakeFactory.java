package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Aquifer;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Water.Lake;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.HashMap;
import java.util.Map;

public class LakeFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("lake", Lake.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            Class lakeClass = bindings.getOrDefault(config.export(Configuration.LAKE), Lake.class);
            Lake lake = ((Lake) lakeClass.getDeclaredConstructor().newInstance());
            lake.process(island, Integer.parseInt(config.export(Configuration.LAKE)));
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

}
