import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = "../generator/sample.mesh";
        String output = "lagoon.mesh";
        Structs.Mesh aMesh = new MeshFactory().read(args[0]);
        Structs.Mesh terrain = new Generator().generate(aMesh);
        MeshFactory terrainFactory = new MeshFactory();
        terrainFactory.write(terrain, args[1]);
    }
}