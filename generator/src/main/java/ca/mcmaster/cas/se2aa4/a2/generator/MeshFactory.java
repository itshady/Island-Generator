package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class MeshFactory {
    public Structs.Mesh generate(String arg) throws IllegalArgumentException {
        if (arg == "square_regular" || arg == "regular") {
            return new SquareRegularMesh().generate();
        } else if (arg == "hex_regular") {
            return new HexRegularMesh().generate();
        } else if (arg == "irregular") {
            return new IrregularMesh().generate();
        }
        throw new IllegalArgumentException("Use --help if you want to know what you can pass in.");
    }
}
