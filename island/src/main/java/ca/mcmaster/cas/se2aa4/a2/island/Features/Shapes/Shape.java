package ca.mcmaster.cas.se2aa4.a2.island.Features.Shapes;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

/**
 * Interface that allows all Shapes to be processed when called in ShapeFactory
 */
public interface Shape {

    void process(Island container);

}
