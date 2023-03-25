package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;


public class AquiferGenerator extends LandWaterGenerator {
    @Override
    protected Integer getLayers() {
        return 1;
    }

    @Override
    protected boolean canBeAdjacentWater() {
        return true;
    }

    @Override
    protected BodyOfWater getNewWater(Integer multiplicity) {
        return new Aquifer();
    }
}
