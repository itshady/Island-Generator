package ca.mcmaster.cas.se2aa4.a2.generator;

public class MeshFactory {

    public Mesh create(TypeOfMesh typeOfMesh) throws IllegalArgumentException {
        return switch (typeOfMesh) {
            case SQUARE, REGULAR -> new SquareRegularMesh();
            case HEX -> new HexRegularMesh();
            case DIAMOND -> new DiamondRegularMesh();
            case IRREGULAR -> new IrregularMesh();
        };
    }
}
