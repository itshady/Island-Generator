package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.AmericaProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.AsiaProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.DiagramProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class BiomeFactory implements FeatureRunner {
    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("america", AmericaProfile.class);
        bindings.put("asia", AsiaProfile.class);
        bindings.put(DEFAULT, AmericaProfile.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            String value = config.export(Configuration.BIOME);
            value = value != null ? value.toLowerCase() : null;
            Class diagramClass = bindings.get(value);
            DiagramProfile diagram = ((DiagramProfile) diagramClass.getDeclaredConstructor().newInstance());
            diagram.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
