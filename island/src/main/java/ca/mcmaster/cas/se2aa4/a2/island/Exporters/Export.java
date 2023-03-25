package ca.mcmaster.cas.se2aa4.a2.island.Exporters;

import Mesh.Mesh;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

public interface Export {

    /**
     * Converts a Structs to a MeshADT type
     * @param mesh, Structs.Mesh
     * @return Mesh, type is MeshADT
     */
    Mesh upgrade(Structs.Mesh mesh);

    /**
     * Converts a MeshADT Mesh to an Island
     * @param mesh, MeshADT
     * @return Island
     */
    Island upgrade(Mesh mesh);

    /**
     * Processes an Island back into MeshADT type
     * @param island, Island
     * @return Mesh, MeshADT
     */
    Mesh process(Island island);

    /**
     * Processes a MeshADT Mesh into a Structs Mesh
     * @param mesh, MeshADT
     * @return Structs.Mesh
     */
    Structs.Mesh process(Mesh mesh);
}
