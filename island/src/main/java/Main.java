import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Export;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Exporter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Visualizer.*;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Buildable;
import ca.mcmaster.cas.se2aa4.a2.island.UI.IslandBuilder;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        Configuration config = new Configuration(args);
        String input = config.export(Configuration.INPUT_MESH);
        String output = config.export(Configuration.OUTPUT_MESH);
        new Seed().process(config.export(Configuration.SEED));
        Export exporter = new Exporter();

        Structs.Mesh aMesh = new MeshFactory().read(input);
        Mesh inputMesh = exporter.upgrade(aMesh);
        Island emptyIsland = exporter.upgrade(inputMesh);

        Buildable specification = new IslandBuilder(emptyIsland, config).create();

        Island richIsland = specification.build();
        String visual = config.export(Configuration.VISUAL).toLowerCase();
        Visualizer visualizer = switch (visual) {
            case "altitude" -> new AltitudeVisualizer();
            case "moisture" -> new MoistureVisualizer();
            case "debug" -> new DebugVisualizer();
            default -> new BiomeVisualizer();
        };

        visualizer.process(richIsland);
        Mesh richMesh = exporter.process(richIsland);
        Structs.Mesh exported = exporter.process(richMesh);

        new MeshFactory().write(exported, output);
    }
}