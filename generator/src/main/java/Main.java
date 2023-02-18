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

        try {
            Map<String, String> parsedArgs = parseArgs(args, options);

            // Extracting command line parameters
            System.out.println(parsedArgs);
            String output = parsedArgs.get("output");
            String meshType = parsedArgs.get("mesh");
            DotGen generator = new DotGen();
            Mesh myMesh = generator.generate(meshType);

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

//        // add option to add n players (each strategy = +1 player)
//        Option option = new Option("p", "players", true, "Add players with respective strategies to game.");
//        option.setArgs(Option.UNLIMITED_VALUES);
//        options.addOption(option);

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
        return argsMap;
    }

}
