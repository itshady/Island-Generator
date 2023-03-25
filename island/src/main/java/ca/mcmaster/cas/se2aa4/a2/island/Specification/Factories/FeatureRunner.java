package ca.mcmaster.cas.se2aa4.a2.island.Specification.Factories;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Configuration.Configuration;

/**
 * Interface that allows all factories, other than SpecificationFactory to be processed when called in SpecificationFactory
 */

public interface FeatureRunner {

    void run(Island island, Configuration config);

}
