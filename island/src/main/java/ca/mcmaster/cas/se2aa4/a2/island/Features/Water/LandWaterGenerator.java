package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.util.*;
import java.util.stream.Collectors;

import static ca.mcmaster.cas.se2aa4.a2.island.TileType.TEST;

public abstract class LandWaterGenerator implements WaterGenerator {
    Island island;

    public void process(Island island, Integer numOfBodies) {
        this.island = island;
        // Iterate n times (user specified)
        // generateWater method
        int i = 0;
        int numOfTiles = island.getTiles().size();

        // try to generate water on every tile until you've checked them all
        while (i < numOfBodies && numOfTiles > 0) {
            if (generateWater(getLayers())) i++;
            numOfTiles--;
        }
    }

    protected abstract Integer getLayers();

    // returns true if water was successfully generated
    // layers = number of layers of how big water will be
    public boolean generateWater(Integer layers) {
        // get random source tile for lake
        List<Tile> landTiles = getLandTiles();
        Tile source = landTiles.get(Seed.nextInt(landTiles.size()));

        List<Tile> sourceNeighbours = source.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList();

        // if source or neighbours are water, then don't make it a water source
        if (source.hasBodyOfWater()) return false;
        if (containsWater(sourceNeighbours)) return false;

        source.setWater(getNewWater());
        //ONLY FOR TESTING
        source.getPolygon().setColor(TEST.toColor());

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
                expandWater(tile, tile.getNeighbours().size(), partOfBodyOfWater);
            }
            currentSetOfSources.addAll(nextSetOfSources);
        }
        return true;
    }

    // expansion = how many tiles to EXPAND by (max), can be lower if no tiles qualified
    private void expandWater(Tile source, Integer expansion, List<Tile> currentBodyOfWater) {
        Integer[] neighbourIds = source.getNeighbours().toArray(new Integer[0]);

        // get random neighbours up to value of amount to expand
        List<Tile> selectedNeighbours = new ArrayList<>();
        while (selectedNeighbours.size() < expansion) {
            int index = Seed.nextInt(neighbourIds.length);
            Tile neighbour = island.getTile(neighbourIds[index]);
            selectedNeighbours.add(neighbour);
        }

        // loop through selected neighbours and give them water
        for (Tile neighbour : selectedNeighbours) {
            if (neighbour.hasBodyOfWater()) continue;
            // get neighbours of the neighbour
            // if any are bodies of water that aren't already part of the current body, don't make this a water
            List<Tile> neighboursNeighbours = new ArrayList<>(neighbour.getNeighbours().stream()
                    .map(id -> island.getTile(id)).toList());
            neighboursNeighbours.removeAll(currentBodyOfWater);

            if (containsWater(neighboursNeighbours) && !canBeAdjacentWater()) continue;

            neighbour.setWater(getNewWater());
            updateTileAltitude(neighbour, source.getCentroid().getAltitude());
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

    protected abstract BodyOfWater getNewWater();

    protected List<Tile> getLandTiles() {
        List<Tile> landTiles = new ArrayList<>();
        for (Tile tile : island.getTiles()) {
            if (tile.getType() == TileType.LAND) {
                landTiles.add(tile);
            }
        }
        return landTiles;
    }
}

