package ca.mcmaster.cas.se2aa4.a2.generator;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;

import java.util.Map;

import static ca.mcmaster.cas.se2aa4.a2.generator.CommandLineOptions.*;
import static ca.mcmaster.cas.se2aa4.a2.generator.TypeOfMesh.IRREGULAR;
import static ca.mcmaster.cas.se2aa4.a2.generator.TypeOfMesh.REGULAR;


public class DotGen {
    // Default Constructor
    public Structs.Mesh generate() {
        Mesh mesh = new MeshFactory().create(REGULAR);
        return mesh.generate();
    }

    // Overloaded for user args
    public Structs.Mesh generate(Map<CommandLineOptions, String> args) {
        TypeOfMesh pattern = TypeOfMesh.valueOf(args.get(TYPEOFMESH).toUpperCase());
        System.out.println(pattern);
        Mesh mesh = new MeshFactory().create(pattern);
        if (pattern == IRREGULAR) {
            if (args.containsKey(NUMOFPOLYGONS)) ((IrregularMesh) mesh).setNumOfPolygons(Integer.parseInt(args.get(NUMOFPOLYGONS)));
            if (args.containsKey(RELAXATION)) ((IrregularMesh) mesh).setRelaxation(Integer.parseInt(args.get(RELAXATION)));
        }
        return mesh.generate();
    }
}