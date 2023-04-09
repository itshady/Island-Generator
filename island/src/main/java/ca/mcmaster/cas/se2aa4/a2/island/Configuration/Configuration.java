package ca.mcmaster.cas.se2aa4.a2.island.Configuration;

import ca.mcmaster.cas.se2aa4.a2.island.Specification.Feature;
import ca.mcmaster.cas.se2aa4.a2.island.Specification.PostFeatures;
import ca.mcmaster.cas.se2aa4.a2.island.Specification.PreFeatures;
import org.apache.commons.cli.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds all CLI arguments needed for island creation
 */

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
    public static final String INPUT_MESH = "i";
    public static final String OUTPUT_MESH = "o";
    public static final String HELP = "help";
    @PreFeatures
    public static final String SEED = "seed";
    @PostFeatures
    public static final String CITIES = "cities";
    public static final String NETWORK = "network";
    @PostFeatures
    public static final String VISUAL = "visual";


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

    /**
     * Stores all CLI arguments and corresponding inputs into a hashmap
     * @return
     */
    public Map<String, String> export() {
        Map<String, String> result = new HashMap<>();
        for(Option o: cli.getOptions()){
            result.put(o.getOpt(), o.getValue(""));
        }
        return result;
    }

    /**
     * Gets the specific value associated with a CLI argument
     * @param key, String
     * @return String
     */
    public String export(String key) {
        return cli.getOptionValue(key);
    }

    private CommandLineParser parser() {
        return new DefaultParser();
    }

    private Options options() {
        Options options = new Options();
        options.addOption(new Option(SHAPE, true, "Shape of island: circle, square, triangle, oval, threecircle"));
        options.addOption(new Option(ALTITUDE, true, "Altitude profile of island"));
        options.addOption(new Option(AQUIFER, true, "Number of aquifers on island"));
        options.addOption(new Option(LAKE, true, "Maximum number of lakes on island"));
        options.addOption(new Option(RIVER, true, "Number of rivers on island"));
        options.addOption(new Option(SOIL, true, "Soil profile of land: Dry, Wet"));
        options.addOption(new Option(BIOME, true, "Selects the whittaker diagram: america, asia"));
        options.addOption(new Option(MODE, true, "Sandbox or Normal mode (defaults to normal)"));
        options.addOption(new Option(INPUT_MESH, true, "Path to input .mesh file"));
        options.addOption(new Option(OUTPUT_MESH, true, "Path to output file name"));
        options.addOption(new Option(SEED, true, "Seed for the island generation"));
        options.addOption(new Option(VISUAL, true, "Visual Mode: Debug, Normal, Altitude, Moisture"));
        options.addOption(new Option(CITIES, true, "Number of cities on island"));
        options.addOption(new Option(NETWORK, true, "Type of network to connect the cities: Star, NonStar"));
        // Global help
        options.addOption(new Option(HELP, false, "print help message"));
        return options;
    }
}
