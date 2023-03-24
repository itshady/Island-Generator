package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.IslandToMeshConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.MeshToIslandConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.MeshToStructsConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.StructsToMeshConverter;

public class Exporter {

    public Exporter() {
    }

    public Mesh upgrade(Structs.Mesh mesh) {
        StructsToMeshConverter converter = new StructsToMeshConverter(mesh);
        return converter.process();
    }

    public Island upgrade(Mesh mesh) {
        MeshToIslandConverter converter = new MeshToIslandConverter();
        return converter.process(mesh);
    }

    public Mesh process(Island island) {
        return new IslandToMeshConverter().process(island);
    }

    public Structs.Mesh process(Mesh mesh) {
        return new MeshToStructsConverter().process(mesh);
    }
}
