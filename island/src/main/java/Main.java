import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Generator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String output = args[1];
        Structs.Mesh aMesh = new MeshFactory().read(input);
        Structs.Mesh terrain = new Generator().generate(aMesh);
        MeshFactory terrainFactory = new MeshFactory();
        terrainFactory.write(terrain, output);
    }
}