package ca.mcmaster.cas.se2aa4.a2.island.UI.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.MountainProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.PrairieProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.*;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.HashMap;
import java.util.Map;

public class AltitudeFactory implements FeatureRunner {
    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("mountain", MountainProfile.class);
        bindings.put("prairie", PrairieProfile.class);
        bindings.put(DEFAULT, MountainProfile.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            Class klass = bindings.get(config.export(Configuration.ALTITUDE));
            ElevationProfile shape = ((ElevationProfile) klass.getDeclaredConstructor().newInstance());
            shape.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
