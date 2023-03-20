package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.DryProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.SoilProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.WetProfile;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.HashMap;
import java.util.Map;

public class SoilFactory implements FeatureRunner {

    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("dry", DryProfile.class);
        bindings.put("wet", WetProfile.class);
        bindings.put(DEFAULT, DryProfile.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            Class soilClass = bindings.get(config.export(Configuration.SOIL));
            SoilProfile soil = ((SoilProfile) soilClass.getDeclaredConstructor().newInstance());
            soil.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
