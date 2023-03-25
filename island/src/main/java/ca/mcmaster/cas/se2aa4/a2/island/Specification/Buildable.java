package ca.mcmaster.cas.se2aa4.a2.island.Specification;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;


/**
 * Interface that allows IslandBuilder to be processed
 */

public interface Buildable {
    Island build();
    Buildable create();
}
