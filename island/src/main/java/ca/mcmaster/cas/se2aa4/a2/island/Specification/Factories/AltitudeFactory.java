package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.CraterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.MountainProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.PrairieProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

public class AltitudeFactory implements FeatureRunner {
    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    static {
        bindings.put("mountain", MountainProfile.class);
        bindings.put("prairie", PrairieProfile.class);
        bindings.put("crater", CraterProfile.class);
        bindings.put(DEFAULT, MountainProfile.class);
    }

    @Override
    public void process(Island island, Configuration config) {
        try {
            String value = config.export(Configuration.ALTITUDE);
            value = value != null ? value.toLowerCase() : null;
            Class altitudeClass = bindings.get(value);
            ElevationProfile shape = ((ElevationProfile) altitudeClass.getDeclaredConstructor().newInstance());
            shape.process(island);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
