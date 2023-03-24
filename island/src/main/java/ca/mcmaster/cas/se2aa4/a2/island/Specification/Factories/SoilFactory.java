package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.DryProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.SoilProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Soil.WetProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

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
            String value = config.export(Configuration.SOIL);
            value = value != null ? value.toLowerCase() : null;
            Class soilClass = bindings.get(value);
            SoilProfile soil = ((SoilProfile) soilClass.getDeclaredConstructor().newInstance());
            soil.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
