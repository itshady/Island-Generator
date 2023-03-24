package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.IslandToMeshConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.MeshToIslandConverter;
import ca.mcmaster.cas.se2aa4.a2.island.Exporters.Converters.StructsToMeshConverter;

public interface Export {
    Mesh upgrade(Structs.Mesh mesh);

    Island upgrade(Mesh mesh);

    Mesh process(Island island);

    Structs.Mesh process(Mesh mesh);
}
