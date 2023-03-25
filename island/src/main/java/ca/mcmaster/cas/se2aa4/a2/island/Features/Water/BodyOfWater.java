package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

/**
 * Interface representing all services that a body of water must contain
 */
public interface BodyOfWater {
    Integer multiplicity();

    boolean isAboveGround();

    // between 0 and 100%
    Integer moisture();

    boolean isLake();

    boolean isRiver();

    boolean isAquifer();

    boolean isOcean();
}
