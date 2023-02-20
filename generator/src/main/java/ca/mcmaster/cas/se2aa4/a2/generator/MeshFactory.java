package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.Map;

public class MeshFactory {

    public Mesh create(TypeOfMesh typeOfMesh) throws IllegalArgumentException {
        return switch (typeOfMesh) {
            case SQUARE, REGULAR -> new SquareRegularMesh();
            case HEX -> new HexRegularMesh();
            case IRREGULAR -> new IrregularMesh();
        };
    }
}
