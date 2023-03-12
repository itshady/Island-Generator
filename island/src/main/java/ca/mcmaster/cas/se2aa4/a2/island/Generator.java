package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

public class Generator {
    public Structs.Mesh generate(Structs.Mesh aMesh) {
        Configuration config = new Configuration()
                .setShape("lagoon");
        Island island = new Island(aMesh, config);
        return island.generate();
    }

}
