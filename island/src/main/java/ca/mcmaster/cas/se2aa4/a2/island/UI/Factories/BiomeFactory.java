package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.AmericaProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.AsiaProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Biomes.DiagramProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.CraterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.MountainProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.PrairieProfile;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

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
            Class diagramClass = bindings.get(config.export(Configuration.BIOME));
            DiagramProfile diagram = ((DiagramProfile) diagramClass.getDeclaredConstructor().newInstance());
            diagram.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
