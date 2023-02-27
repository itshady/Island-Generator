import ca.mcmaster.cas.se2aa4.a2.generator.Enums.CommandLineOptions;
import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.generator.Enums.TypeOfMesh;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static ca.mcmaster.cas.se2aa4.a2.generator.Enums.CommandLineOptions.*;
import static ca.mcmaster.cas.se2aa4.a2.generator.Enums.TypeOfMesh.IRREGULAR;
import static ca.mcmaster.cas.se2aa4.a2.generator.Enums.TypeOfMesh.SQUARE;

public class Main {
    public static void main(String[] args) {
        Options options = setupCLI();

        try {
            // Extracting command line parameters
            Map<CommandLineOptions, String> parsedArgs = parseArgs(args, options);

            if (parsedArgs.containsKey(HELP)) return;
            String output = parsedArgs.get(OUTPUTFILE);
            Mesh myMesh = new DotGen().generate(parsedArgs);
            new MeshFactory().write(myMesh, output);

        } catch (ParseException | IOException e) {
             System.out.println(e);
        }
    }

    private static Options setupCLI() {
        Options options = new Options();

        // add option to specify mesh file
        options.addOption("o","output", true, "Specify output file.");

        // add option to select the type of mesh
        options.addOption("m", "mesh", true, "Selects the type of mesh to output: hex_regular, square_regular, or irregular" );

        // add option to select the number of polygons
        options.addOption("p", "polygons", true, "Enter the number of polygons.");

        // add option to select the relaxation level
        options.addOption("r", "relaxation", true, "Enter the relaxation level for lloyd's relaxation");

        // add option for logging
        options.addOption("h","help", false, "Ask for usage help.");

        return options;
    }

    private static Map<CommandLineOptions, String> parseArgs(String[] args, Options options) throws ParseException {
        Map<CommandLineOptions, String> argsMap = new HashMap<>();
        //Create a parser
        CommandLineParser parser = new DefaultParser();
        TypeOfMesh typeOfMesh = SQUARE;
        //parse the options passed as command line arguments
        CommandLine cmd = parser.parse(options, args);
        if(cmd.hasOption("h")) {
            String usage = "mvn exec:java -Dexec.args=\"[OPTIONS]\"";
            String header = "Options:\n";
            String footer = "";

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage, header, options, footer, false);
            argsMap.put(HELP, "true");
        } else {
            if (cmd.hasOption("o")) {
                argsMap.put(OUTPUTFILE, cmd.getOptionValues("o")[0]);
            }
            if (cmd.hasOption("m") && validArg(TYPEOFMESH, cmd.getOptionValues("m")[0])) {
                typeOfMesh = TypeOfMesh.valueOf(cmd.getOptionValues("m")[0].toUpperCase());
            }
            if (cmd.hasOption("p") && validArg(NUMOFPOLYGONS, cmd.getOptionValues("p")[0])) {
                if (typeOfMesh == IRREGULAR) argsMap.put(NUMOFPOLYGONS, cmd.getOptionValues("p")[0]);
                else System.out.println("You can't pick the number of polygons for a regular mesh. Continuing ignoring this param...");
            }
            if (cmd.hasOption("r")) {
                if (typeOfMesh == IRREGULAR) argsMap.put(RELAXATION, cmd.getOptionValues("r")[0]);
                else System.out.println("You can't set a relaxation value for a regular mesh. Continuing ignoring this param...");
            }
            argsMap.put(TYPEOFMESH, typeOfMesh.toString());
        }
        return argsMap;
    }

    private static Boolean validArg(CommandLineOptions argType, String argument) {
        try {
            switch (argType) {
                case NUMOFPOLYGONS:
                case RELAXATION:
                    int numOfPolygons = Integer.parseInt(argument);
                    if (numOfPolygons <= 0) throw new IllegalArgumentException("Can't have a negative number of polygons or relaxation.");
                    break;
                case TYPEOFMESH:
                    // will throw exception if its not in the list of enums
                    try {
                        TypeOfMesh.valueOf(argument.toUpperCase());
                    } catch (Exception e) {
                        throw new IllegalArgumentException("Incorrect pattern name. That pattern doesn't exist.");
                    }
                    break;
                case OUTPUTFILE:
                    if (!argument.endsWith(".mesh")) throw new IllegalArgumentException("Must provide a .mesh file as output");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
