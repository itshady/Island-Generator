package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Circle;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Lagoon;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Oval;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Shape;
import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

public class Configuration {
    @Feature
    public static final String SHAPE = "shape";
    @Feature
    public static final String ALTITUDE = "altitude";
    public static final String MODE = "mode";
    public static final String INPUT_MESH = "i";
    public static final String OUTPUT_MESH = "o";
    public static final String HELP = "help";

    private CommandLine cli;

    public Configuration(String[] args) {
        try {
            this.cli = parser().parse(options(), args); // sets up CLI and parses args
            if (cli.hasOption(HELP)) {
                help();
            }
        } catch (ParseException pe) {
            throw new IllegalArgumentException(pe);
        }
    }

    private void help() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar generator.jar", options());
        System.exit(0);
    }

    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    public String export(String key) {
        return cli.getOptionValue(key);
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(SHAPE, true, "Shape of island"));
        options.addOption(new Option(ALTITUDE, true, "Maximum altitude of island"));
        options.addOption(new Option(MODE, true, "Sandbox or Normal mode"));
        options.addOption(new Option(INPUT_MESH, true, "Path to input .mesh file"));
        options.addOption(new Option(OUTPUT_MESH, true, "Path to output file name"));
        // Global help
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }
}
