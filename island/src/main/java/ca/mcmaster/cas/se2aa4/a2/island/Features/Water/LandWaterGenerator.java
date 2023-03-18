package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import Geometries.Segment;
import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.util.*;
import java.util.stream.Collectors;

public abstract class LandWaterGenerator implements WaterGenerator {
    Island island;

    public void process(Island island, Integer numOfBodies) {
        this.island = island;
        // Iterate n times (user specified)
        // generateWater method
        System.out.println(numOfBodies);
        int i = 0;
        Integer numOfTiles = island.getTiles().size();

        // try to generate water on every tile until you've checked them all
        while (i < numOfBodies && numOfTiles > 0) {
            System.out.println("LOOP");
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
        for (Tile tile : sourceNeighbours) {
//            System.out.println("HEREEEE111");
//            System.out.println(source);
//            System.out.println(source.hasBodyOfWater());
//            System.out.println(tile);
//            System.out.println(tile.hasLake() +" "+ tile.hasAquifer() + " " + tile.isOcean());
//            System.out.println(containsOcean(sourceNeighbours));
//            System.out.println(containsLake(sourceNeighbours));
        }
        // if source or neighbours are water, then don't make it a water source
        if (source.hasBodyOfWater()) return false;
        if (containsOcean(sourceNeighbours) || containsLake(sourceNeighbours)) return false;
        source.setWater(getNewWater());
        expandWater(source);
        return true;
    }

    private void expandWater(Tile source) {
        Integer[] neighbourIds = source.getNeighbours().toArray(new Integer[0]);

        // generate a random number of values to select
        int numValues = Seed.nextInt(neighbourIds.length + 1);

        // get random neighbours up to value above
        List<Tile> selectedNeighbours = new ArrayList<>();
        while (selectedNeighbours.size() < numValues) {
            int index = Seed.nextInt(neighbourIds.length);
            Tile neighbour = island.getTile(neighbourIds[index]);
            selectedNeighbours.add(neighbour);
        }

        // loop through selected neighbours and give them water
        for (Tile neighbour : selectedNeighbours) {
            if (neighbour.hasBodyOfWater()) continue;
            // get neighbours of neighbour that aren't the sources neighbours
            // if any are bodies of water, don't make this a water
            List<Tile> uniqueNeighbours = getDifferentNeighbours(source, neighbour);
            if (containsLake(uniqueNeighbours) || containsOcean(uniqueNeighbours)) continue;
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

    private List<Tile> getDifferentNeighbours(Tile tile1, Tile tile2) {
        List<Tile> tile1Neighbours = tile1.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList();
        List<Tile> tile2Neighbours = tile2.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList();

        List<Tile> uniques = new ArrayList<>(tile1Neighbours);
        uniques.removeAll(tile2Neighbours);

        // remove the two input tiles
        uniques.remove(tile1);
        uniques.remove(tile2);
        return uniques;
    }

    private boolean containsLake(List<Tile> tiles) {
        for (Tile neighbour : tiles) {
            if (neighbour.hasLake()) return true;
        }
        return false;
    }

    private boolean containsOcean(List<Tile> tiles) {
        for (Tile neighbour : tiles) {
            if (neighbour.isOcean()) return true;
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

