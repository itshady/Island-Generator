package ca.mcmaster.cas.se2aa4.a2.island.UI;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Lagoon;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Shape;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.ThreeCircle;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes.Triangle;

public class IslandBuilder implements Buildable {
    Island island;

    // Configuration Attributes
    Shape shape;

    public IslandBuilder(Island emptyIsland, Configuration configuration) { // pass in Configuration
        island = emptyIsland;
        configure(configuration);

    }

    private void configure(Configuration configuration) {
        // feature = configuration.getFeature()
//        shape = configuration.getShape();
        shape = new Lagoon();
    }



    public Buildable create() {
        process();
        return this;
    }

    private void process() {
        shape.process(island);
    }

    @Override
    public Island build() {
        return island;
    }

    // Iterable Tiles
}
