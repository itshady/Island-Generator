package ca.mcmaster.cas.se2aa4.a2.island.Specification;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

public interface Buildable {
    Island build();
    Buildable create();
}
