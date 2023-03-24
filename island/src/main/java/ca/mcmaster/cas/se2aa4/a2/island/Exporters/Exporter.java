package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.IslandToMeshConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.MeshToIslandConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.MeshToStructsConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.StructsToMeshConverter;

public class Exporter implements Export {
    public Mesh upgrade(Structs.Mesh mesh) {
        return new StructsToMeshConverter().process(mesh);
    }

    public Island upgrade(Mesh mesh) {
        return new MeshToIslandConverter().process(mesh);
    }

    public Mesh process(Island island) {
        return new IslandToMeshConverter().process(island);
    }

    public Structs.Mesh process(Mesh mesh) {
        return new MeshToStructsConverter().process(mesh);
    }
}
