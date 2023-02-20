package ca.mcmaster.cas.se2aa4.a2.generator;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;

public class DotGen {

    public Structs.Mesh generate() {
        return new MeshFactory().generate("irregular");
    }
}