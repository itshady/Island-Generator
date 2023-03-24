package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.UI.Factories.SpecificationFactory;

import java.lang.reflect.Field;

public class IslandBuilder implements Buildable {
    Island island;

    public IslandBuilder(Island emptyIsland, Configuration configuration) { // pass in Configuration
        island = emptyIsland;
        configure(configuration);
    }

    private void configure(Configuration configuration) {
        for(Field candidate: configuration.getClass().getFields()) {
            if(candidate.isAnnotationPresent(Feature.class)) {
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
