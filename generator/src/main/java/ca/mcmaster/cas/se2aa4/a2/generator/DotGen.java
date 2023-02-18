package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class DotGen {

    private String meshType;

    // Set this one as the default
    public Structs.Mesh generate() {
        return new MeshFactory().generate("irregular");
    }

    // Overloading the generate method
    public Structs.Mesh generate(String meshType) {
        return new MeshFactory().generate(meshType);
    }
}