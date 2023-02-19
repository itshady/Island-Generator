package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.Map;

public class DotGen {

    // Set this one as the default
    public Structs.Mesh generate() {
        return new MeshFactory().generate("regular");
    }

    // Overloading the generate method with number of polygons
    public Structs.Mesh generate(Map<String, String> argOptions) {
        return new MeshFactory().generate(argOptions);
    }
}