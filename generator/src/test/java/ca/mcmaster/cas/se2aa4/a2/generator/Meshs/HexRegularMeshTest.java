package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import Mesh.Mesh;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HexRegularMeshTest {
    @Test
    void generateHex() {
        Mesh mesh = new HexRegularMesh();
        assertNotNull(mesh.generate());
    }
}