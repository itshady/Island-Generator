package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import Mesh.Mesh;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HoneyCombRegularMeshTest {
    @Test
    void generateIrregular() {
        Mesh mesh = new HoneyCombRegularMesh();
        assertNotNull(mesh.generate());
    }
}