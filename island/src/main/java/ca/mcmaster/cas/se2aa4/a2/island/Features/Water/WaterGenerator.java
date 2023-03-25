package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

/**
 * Interface that allows all bodies of water to be processed when called in their corresponding Factory
 */
public interface WaterGenerator {

    void process(Island island, Integer nums);

}
