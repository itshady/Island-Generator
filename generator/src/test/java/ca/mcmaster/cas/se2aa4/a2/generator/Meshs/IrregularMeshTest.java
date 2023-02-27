package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IrregularMeshTest {
    @Test
    void generateIrregular() {
        Mesh mesh = new IrregularMesh();
        assertNotNull(mesh.generate());
    }
}