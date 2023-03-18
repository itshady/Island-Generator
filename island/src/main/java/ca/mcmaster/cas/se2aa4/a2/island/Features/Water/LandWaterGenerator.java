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
            if (generateWater()) i++;
            numOfTiles--;
        }
    }

    // returns true if water was successfully generated
    public boolean generateWater() {
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
        expandWater(source);
        return true;
    }

    private void expandWater(Tile source) {
        Integer[] neighbourIds = source.getNeighbours().toArray(new Integer[0]);

        // generate a random number of values to select
        int numValues = Seed.nextInt(Math.max(neighbourIds.length-2, 0), neighbourIds.length + 1);

        // get random neighbours up to value above
        List<Tile> selectedNeighbours = new ArrayList<>();
        while (selectedNeighbours.size() < numValues) {
            int index = Seed.nextInt(neighbourIds.length);
            Tile neighbour = island.getTile(neighbourIds[index]);
            selectedNeighbours.add(neighbour);
        }

        List<Tile> currentBodyOfWater = new ArrayList<>();
        currentBodyOfWater.add(source);

        // loop through selected neighbours and give them water
        for (Tile neighbour : selectedNeighbours) {
            if (neighbour.hasBodyOfWater()) continue;
            // get neighbours of the neighbour
            // if any are bodies of water that aren't already part of the current body, don't make this a water
            List<Tile> neighboursNeighbours = new ArrayList<>(neighbour.getNeighbours().stream()
                    .map(id -> island.getTile(id)).toList());
            neighboursNeighbours.removeAll(currentBodyOfWater);

            if (containsWater(neighboursNeighbours)) continue;

            neighbour.setWater(getNewWater());
            updateTileAltitude(neighbour, source.getCentroid().getAltitude());
        }
    }

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

    private List<Tile> getDifferentNeighbours(List<Tile> tile1Neighbours, List<Tile> tile2Neighbours) {
        List<Tile> uniques = new ArrayList<>(tile1Neighbours);
        uniques.removeAll(tile2Neighbours);

        return uniques;
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

