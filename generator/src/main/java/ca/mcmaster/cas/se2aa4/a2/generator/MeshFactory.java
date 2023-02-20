package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.Map;

public class MeshFactory {

    public Structs.Mesh generate(String arg) throws IllegalArgumentException {
        if (arg.equals("square_regular") || arg.equals("regular")) {
            return new SquareRegularMesh().generate();
        } else if (arg.equals("hex_regular")) {
            return new HexRegularMesh().generate();
        } else if (arg.equals("irregular")) {
            return new IrregularMesh().generate();
        }
        throw new IllegalArgumentException("Use --help if you want to know what you can pass in.");
    }

    // Overloaded method for command line arguments
    public Structs.Mesh generate(Map<String, String> argOptions) throws IllegalArgumentException {
        String mesh = argOptions.get("mesh");
        Integer relaxation = Integer.parseInt(argOptions.get("relaxation"));
        Integer numOfPolygons = Integer.parseInt(argOptions.get("polygons"));
        if (mesh.equals("square_regular") || mesh.equals("regular")) {
            return new SquareRegularMesh().generate();
        } else if (mesh.equals("hex_regular")) {
            return new HexRegularMesh().generate();
        } else if (mesh.equals("irregular")) {
            return new IrregularMesh().generate(relaxation, numOfPolygons);
        }
        throw new IllegalArgumentException("Use --help if you want to know what you can pass in.");
    }
}
