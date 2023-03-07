import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class Generator {
    public Structs.Mesh generate(Structs.Mesh aMesh) {
        Structs.Mesh terrain = (new Lagoon()).generateLagoon(aMesh);
        return terrain;
    }

}
