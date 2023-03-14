package ca.mcmaster.cas.se2aa4.a2.generator.Meshs;

import Mesh.Mesh;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SquareRegularMeshTest {
    @Test
    void generateSquare() {
        Mesh mesh = new SquareRegularMesh();
        assertNotNull(mesh.generate());
    }
}