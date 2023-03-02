import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String output = args[1];
        if (args[1] == null) {
            output = "lagoon.mesh";
        }
//        System.out.println("input: " + input);
//        System.out.println("output: " + output);


        Structs.Mesh aMesh = new MeshFactory().read(input);

        Structs.Mesh terrain = (new Lagoon()).generateLagoon(aMesh);
        MeshFactory factory = new MeshFactory();
        factory.write(terrain, output);
    }
}
