package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.CraterProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.ElevationProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.MountainProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation.PrairieProfile;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory for altitude profiles
 */

public class AltitudeFactory implements FeatureRunner {
    private static final Map<String, Class> bindings = new HashMap<>();
    private static final String DEFAULT = null;

    // Puts all the necessary altitude profiles into the bindings map
    static {
        bindings.put("mountain", MountainProfile.class);
        bindings.put("prairie", PrairieProfile.class);
        bindings.put("crater", CraterProfile.class);
        bindings.put(DEFAULT, MountainProfile.class);
    }

    /**
     * Runs the designated altitude profile on the given island based on its configuration
     * @param island: Island to execute feature on
     * @param config: Specified altitude profile
     */
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
