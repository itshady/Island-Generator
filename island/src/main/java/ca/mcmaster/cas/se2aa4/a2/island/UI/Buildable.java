package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;

public interface Buildable {
    Island build();
    Buildable create();
}
