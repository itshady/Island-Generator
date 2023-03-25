package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.util.*;

public abstract class LandWaterGenerator implements WaterGenerator {
    Island island;
    List<Tile> uncheckedTiles;

    public void process(Island island, Integer numOfBodies) {
        this.island = island;
        uncheckedTiles = new ArrayList<>(island.getTiles());
        // Iterate n times (user specified)
        // generateWater method
        int i = 0;

        // try to generate water on every tile until you've checked them all
        while (i < numOfBodies && !uncheckedTiles.isEmpty()) {
            if (generateWater(getLayers())) i++;
        }
    }

    protected abstract Integer getLayers();

    // returns true if water was successfully generated
    // layers = number of layers of how big water will be
    public boolean generateWater(Integer layers) {
        // get random source tile for lake
        List<Tile> landTiles = getLandTiles();
        Seed seed = Seed.getInstance();
        Tile source = landTiles.get(seed.nextInt(landTiles.size()));

        List<Tile> sourceNeighbours = source.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList();

        // remove it from the list of tiles to check
        uncheckedTiles.remove(source);
        // if source or neighbours are water, then don't make it a water source
        if (source.hasBodyOfWater()) return false;
        if (containsWater(sourceNeighbours)) return false;
        source.setWater(getNewWater(1));
        source.setWaterCenter(true);

        // start with only expanding source
        List<Tile> currentSetOfSources = new ArrayList<>();
        currentSetOfSources.add(source);

        // go through each "layer" of neighbours and expand
        for (int i=0; i<layers; i++) {
            List<Tile> nextSetOfSources = new ArrayList<>();
            for (Tile tile : currentSetOfSources) {
                nextSetOfSources.addAll(tile.getNeighbours().stream()
                        .map(id -> island.getTile(id)).toList());
                List<Tile> partOfBodyOfWater = new ArrayList<>();
                partOfBodyOfWater.addAll(nextSetOfSources);
                partOfBodyOfWater.addAll(currentSetOfSources);
                expandWater(tile, tile.getNeighbours().size(), partOfBodyOfWater, Math.abs(i-layers));
            }
            currentSetOfSources.addAll(nextSetOfSources);
        }
        return true;
    }

    // expansion = how many tiles to EXPAND by (max), can be lower if no tiles qualified
    private void expandWater(Tile source, Integer expansion, List<Tile> currentBodyOfWater, Integer multiplicity) {
        Integer[] neighbourIds = source.getNeighbours().toArray(new Integer[0]);

        // get random neighbours up to value of amount to expand
        List<Tile> selectedNeighbours = new ArrayList<>();
        while (selectedNeighbours.size() < expansion) {
            Seed seed = Seed.getInstance();
            int index = seed.nextInt(neighbourIds.length);
            Tile neighbour = island.getTile(neighbourIds[index]);
            selectedNeighbours.add(neighbour);
        }

        // loop through selected neighbours and give them water
        for (Tile neighbour : selectedNeighbours) {
            if (neighbour.hasBodyOfWater() || (hasOceanNeighbours(neighbour) && !canBeAdjacentWater())) continue;
            // get neighbours of the neighbour
            // if any are bodies of water that aren't already part of the current body, don't make this a water
            List<Tile> neighboursNeighbours = new ArrayList<>(neighbour.getNeighbours().stream()
                    .map(id -> island.getTile(id)).toList());
            neighboursNeighbours.removeAll(currentBodyOfWater);

            if (containsWater(neighboursNeighbours) && !canBeAdjacentWater()) continue;

            neighbour.setWater(getNewWater(multiplicity));
            if (neighbour.hasLake()) updateTileAltitude(neighbour, source.getCentroid().getAltitude());
        }
    }

    protected abstract boolean canBeAdjacentWater();

    /**
     * Updates all the associated vertices and the centroid of the tile to the given altitude
     * @param tile The tile to update
     * @param altitude The altitude to update to
     */
    private void updateTileAltitude(Tile tile, Integer altitude) {
        tile.getCentroid().setAltitude(altitude);

        for (Border border : tile.getBorders()) {
            border.getV1().setAltitude(altitude);
            border.getV2().setAltitude(altitude);
        }
    }

    private boolean containsWater(List<Tile> tiles) {
        for (Tile neighbour : tiles) {
            if (neighbour.hasBodyOfWater()) return true;
        }
        return false;
    }

    protected abstract BodyOfWater getNewWater(Integer multiplicity);

    protected List<Tile> getLandTiles() {
        List<Tile> landTiles = new ArrayList<>();
        for (Tile tile : island.getTiles()) {
            if (tile.isLand()) {
                landTiles.add(tile);
            }
        }
        return landTiles;
    }

    private boolean hasOceanNeighbours(Tile tile) {
        List<Tile> neighbours = new ArrayList<>(tile.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList());
        for (Tile neighbour : neighbours) {
            if (neighbour.isOcean()) return true;
        }
        return false;
    }
}

