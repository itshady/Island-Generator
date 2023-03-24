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

    @Feature
    public static final String AQUIFER = "aquifer";

    @Feature
    public static final String LAKE = "lake";
    @Feature
    public static final String RIVER = "river";
    @Feature
    public static final String SOIL = "soil";
    @Feature
    public static final String BIOME = "biomes";
    public static final String MODE = "mode";
    @PostFeatures
    public static final String VISUAL = "visual";
    public static final String INPUT_MESH = "i";
    public static final String OUTPUT_MESH = "o";
    public static final String HELP = "help";
    @PreFeatures
    public static final String SEED = "seed";


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
        options.addOption(new Option(ALTITUDE, true, "Altitude profile of island"));
        options.addOption(new Option(AQUIFER, true, "Number of aquifers on island"));
        options.addOption(new Option(LAKE, true, "Maximum number of lakes on island"));
        options.addOption(new Option(RIVER, true, "Number of rivers on island"));
        options.addOption(new Option(SOIL, true, "Soil profile of land"));
        options.addOption(new Option(BIOME, true, "Selects the whittaker diagram"));
        options.addOption(new Option(MODE, true, "Sandbox or Normal mode"));
        options.addOption(new Option(INPUT_MESH, true, "Path to input .mesh file"));
        options.addOption(new Option(OUTPUT_MESH, true, "Path to output file name"));
        options.addOption(new Option(SEED, true, "Seed for the island generation"));
        options.addOption(new Option(VISUAL, true, "Visual Mode: Debug, Normal, Altitude, Moisture"));
        // Global help
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }
}
