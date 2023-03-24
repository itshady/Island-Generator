package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

public interface Export {
    Mesh upgrade(Structs.Mesh mesh);

    Island upgrade(Mesh mesh);

    Mesh process(Island island);

    Structs.Mesh process(Mesh mesh);
}
