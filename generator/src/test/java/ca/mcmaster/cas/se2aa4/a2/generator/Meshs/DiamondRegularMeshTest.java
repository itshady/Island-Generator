package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import Mesh.Mesh;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiamondRegularMeshTest {
    @Test
    void generateDiamond() {
        Mesh mesh = new DiamondRegularMesh();
        assertNotNull(mesh.generate());
    }
}