import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Exporter;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Buildable;
import ca.mcmaster.cas.se2aa4.a2.island.UI.IslandBuilder;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String input = args[0];
        String output = args[1];
        Configuration config = new Configuration(args);
        Exporter exporter = new Exporter();
        Structs.Mesh aMesh = new MeshFactory().read(input);
        Mesh inputMesh = exporter.upgrade(aMesh);
        Island emptyIsland = exporter.upgrade(inputMesh);
        Buildable specification = new IslandBuilder(emptyIsland, config).create();
        Island richIsland = specification.build();
        Mesh richMesh = exporter.process(richIsland);
        Structs.Mesh exported = exporter.process(richMesh);
        new MeshFactory().write(exported, output);    //config.export(Configuration.OUTPUT_MESH)


//        Structs.Mesh aMesh = new MeshFactory().read(input);
//        Structs.Mesh terrain = new Generator().generate(aMesh);
//        MeshFactory terrainFactory = new MeshFactory();
//        terrainFactory.write(terrain, output);
    }
}