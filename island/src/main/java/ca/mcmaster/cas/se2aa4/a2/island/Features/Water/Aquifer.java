package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

public class Aquifer extends LandWater{


    public void process(Island island, Tile tile) {
        this.island = island;
    }


}
