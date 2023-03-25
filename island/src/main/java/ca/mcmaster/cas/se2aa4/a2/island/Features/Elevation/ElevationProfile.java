package ca.mcmaster.cas.se2aa4.a2.island.Features.Elevation;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

/**
 * Interface that allows all ElevationProfiles to be processed when called in AltitudeFactory
 */
public interface ElevationProfile {
    void process(Island container);
}
