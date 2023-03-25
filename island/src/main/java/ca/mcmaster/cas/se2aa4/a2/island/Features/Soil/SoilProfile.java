package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;

/**
 * Interface that allows all Shapes to be processed when called in ShapeFactory
 */
public interface SoilProfile {
    void process(Island container);
}
