import ca.mcmaster.cas.se2aa4.a2.generator.DotGen;
import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.io.Structs.Mesh;
import org.apache.commons.cli.*;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {



    public static void main(String[] args) {
        Options options = setupCLI();
        MeshFactory factory = new MeshFactory();
        DotGen generator = new DotGen();

        try {
            Map<String, String> parsedArgs = parseArgs(args, options);

            // Extracting command line parameters
            System.out.println(parsedArgs);
            String output = parsedArgs.get("output");
            Mesh myMesh = generator.generate(parsedArgs);
            factory.write(myMesh, output);

        } catch (Exception e) {
             System.out.println(e);
        }
    }

    private static Options setupCLI() {
        Options options = new Options();


        // add option for debugging
        options.addOption("X","debug", false, "Turns debug mode on.");

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

    private static Map<String, String> parseArgs(String[] args, Options options) throws ParseException {
        Map<String, String> argsMap = new HashMap<>();
        //Create a parser
        CommandLineParser parser = new DefaultParser();

        //parse the options passed as command line arguments
        CommandLine cmd = parser.parse(options, args);
        if(cmd.hasOption("h")) {
            String usage = "mvn exec:java -Dexec.args=\"[-d] [-n] <NUM> [-p] <STRATEGY>\"";
            String header = "Options:\n";
            String footer = "";

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage, header, options, footer, false);
        }
        if(cmd.hasOption("X")) {
            // if debug option passed
            argsMap.put("debug","true");
        }
        if(cmd.hasOption("o")) {
            // if debug option passed
            argsMap.put("output",cmd.getOptionValues("o")[0]);
        }
        if(cmd.hasOption("m")) {
            argsMap.put("mesh", cmd.getOptionValues("m")[0]);
        }
        if(cmd.hasOption("p")) {
            if(cmd.hasOption("m")) {
                if (cmd.getOptionValues("m")[0].equals("irregular")) {
                    argsMap.put("polygons", cmd.getOptionValues("p")[0]);
                } else {
                    throw new ParseException("You can't pick the number of polygons for a regular mesh");
                }
            }
        }
        if (cmd.hasOption("r")) {
            if (cmd.hasOption("m")) {
                if (cmd.getOptionValues("m")[0].equals("irregular")) {
                    argsMap.put("relaxation", cmd.getOptionValues("r")[0]);
                } else {
                    throw new ParseException("You can't set a relaxation value for a regular mesh");
                }
            }
        }
        return argsMap;
    }

}
