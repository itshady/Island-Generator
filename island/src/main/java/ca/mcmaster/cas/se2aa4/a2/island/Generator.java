package ca.mcmaster.cas.se2aa4.a2.island;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Generator {
    public Structs.Mesh generate(Structs.Mesh aMesh) {
//        Structs.Mesh terrain = (new Lagoon()).generateLagoon(aMesh);
        Island island = new Island(aMesh, new Configuration());
        return island.generate();
    }

}
