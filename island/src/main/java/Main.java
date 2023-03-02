import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = "../generator/sample.mesh";
        String output = "lagoon.mesh";
        MeshFactory terrainFactory = new MeshFactory();
        Structs.Mesh aMesh = terrainFactory.read(input);
        Structs.Mesh terrain = new Generator().generate(aMesh);
        terrainFactory.write(terrain, args[1]);
    }
}