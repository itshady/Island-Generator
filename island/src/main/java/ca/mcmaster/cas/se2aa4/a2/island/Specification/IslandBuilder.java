package ca.mcmaster.cas.se2aa4.a2.island.Specification;

import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;
import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Visualizer.LagoonVisualizer;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Lagoon;
import ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories.SpecificationFactory;

import java.lang.reflect.Field;

public class IslandBuilder implements Buildable {
    Island island;

    /**
     * Initializes the island and calls the configure method to create a "rich" island
     * @param emptyIsland: The island to be modified with all configurations.
     * @param configuration: The desired configurations
     */
    public IslandBuilder(Island emptyIsland, Configuration configuration) { // pass in Configuration
        island = emptyIsland;
        configure(configuration);
    }

    /**
     * Builds an island based on the command line arguments provided
     * @param configuration: Command line argument configuration
     */

    private void configure(Configuration configuration) {
        // Run before all features
        for(Field candidate: configuration.getClass().getFields()) {
            if(candidate.isAnnotationPresent(PreFeatures.class)) {
                SpecificationFactory.run(candidate, island, configuration);
            }
        }

        // Sandbox mode if lagoon command line argument is found. Ignore all other features
        String mode = configuration.export(Configuration.MODE);
        if (mode != null && mode.equalsIgnoreCase("lagoon")) {
            new Lagoon().process(island);
            new LagoonVisualizer().process(island);
            return;
        }

        // Run all features
        for(Field candidate: configuration.getClass().getFields()) {
            if(candidate.isAnnotationPresent(Feature.class)) {
                SpecificationFactory.run(candidate, island, configuration);
            }
        }

        // Run after all features
        for(Field candidate: configuration.getClass().getFields()) {
            if(candidate.isAnnotationPresent(PostFeatures.class)) {
                SpecificationFactory.run(candidate, island, configuration);
            }
        }
    }

    public Buildable create() {
        return this;
    }

    @Override
    public Island build() {
        return island;
    }
}
