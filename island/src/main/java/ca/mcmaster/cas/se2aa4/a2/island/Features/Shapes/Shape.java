package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.ADTContainer;

public interface Shape {
    // manipulate the polygons in order to make the island a shape
    void process(ADTContainer container);

    void initializeLand();
}
