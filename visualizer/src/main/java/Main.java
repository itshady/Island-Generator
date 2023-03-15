import ca.mcmaster.cas.se2aa4.a2.io.MeshFactory;
import ca.mcmaster.cas.se2aa4.a2.io.Structs;
import ca.mcmaster.cas.se2aa4.a2.visualizer.GraphicRenderer;
import ca.mcmaster.cas.se2aa4.a2.visualizer.utils.MeshDump;
import ca.mcmaster.cas.se2aa4.a2.visualizer.utils.SVGCanvas;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Options options = setupCLI();
        GraphicRenderer renderer = new GraphicRenderer();

        try {
            Map<String, String> parsedArgs = parseArgs(args, options);
            if (!parsedArgs.containsKey("help")) {
                if (Objects.equals(parsedArgs.get("debug"), "true")) renderer.turnOnDebug();

                // Extracting command line parameters
                String input = parsedArgs.get("mesh");
                String output = parsedArgs.get("output");

                // Getting width and height for the canvas
                Structs.Mesh aMesh = new MeshFactory().read(input);
                double max_x = Double.MIN_VALUE;
                double max_y = Double.MIN_VALUE;
                for (Structs.Vertex v : aMesh.getVerticesList()) {
                    max_x = (Double.compare(max_x, v.getX()) < 0 ? v.getX() : max_x);
                    max_y = (Double.compare(max_y, v.getY()) < 0 ? v.getY() : max_y);
                }
                // Creating the Canvas to draw the mesh
                Graphics2D canvas = SVGCanvas.build((int) Math.ceil(max_x), (int) Math.ceil(max_y));
                // Painting the mesh on the canvas
                renderer.render(aMesh, canvas);
                // Storing the result in an SVG file
                SVGCanvas.write(canvas, output);
                // Dump the mesh to stdout
                MeshDump dumper = new MeshDump();
//                dumper.dump(aMesh);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    private static Options setupCLI() {
        Options options = new Options();


        // add option for debugging
        options.addOption("X","debug", false, "Turns debug mode on.");

        // add option to specify mesh file
        options.addOption("m","mesh", true, "Specify the mesh file.");

        // add option to specify output svg file
        options.addOption("o","output", true, "Specify output svg file.");

        // add option for help
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
            String usage = "mvn exec:java -Dexec.args=\"[OPTIONS]\"";
            usage += "\nOR  java -jar visualizer.jar [OPTIONS]";
            String header = "Options:\n";
            String footer = "";

            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp(usage, header, options, footer, false);
            argsMap.put("help", "true");
        }
        if(cmd.hasOption("X")) {
            // if debug option passed
            argsMap.put("debug","true");
        }
        if(cmd.hasOption("m")) {
            // if debug option passed
            argsMap.put("mesh",cmd.getOptionValues("m")[0]);
        }
        if(cmd.hasOption("o")) {
            // if debug option passed
            argsMap.put("output",cmd.getOptionValues("o")[0]);
        }
        return argsMap;
    }
}
