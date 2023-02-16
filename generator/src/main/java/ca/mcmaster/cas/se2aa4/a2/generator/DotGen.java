package ca.mcmaster.cas.se2aa4.a2.generator;

import java.awt.*;
import java.util.*;
import java.util.List;

import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.generator.Mesh;


public class DotGen {

    public Structs.Mesh generate() {
        return new MeshFactory().generate("irregular");
    }
}