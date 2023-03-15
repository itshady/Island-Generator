package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class LandWaterGenerator implements BodiesOfWater {

    Island island;

    List<Tile> tiles;

    private final Integer waterSize = 5;
    List<Tile> landTiles = new ArrayList<>();

    public void process(Island island) {
        this.island = island;
        tiles = island.getTiles();
        determineLandTiles();

        // Iterate n times (user specified)
        // generateWater method

        for (int i = 0; i < 3; i++) {
            generateWater();
        }

    }

    protected void determineLandTiles() {
        for (Tile tile : tiles) {
            if (tile.getType() == TileType.LAND) {
                landTiles.add(tile);
            }
        }
    }

    protected abstract void setWater(Tile tile);

    protected void generateWater() {
        Random random = new Random();
        Tile sourceTile = landTiles.get(random.nextInt(landTiles.size()));
        setWater(sourceTile);
    }

//        while (currentWaterSize <= waterSize) {
//            Set<Integer> neighbours = sourceTile.getNeighbours();
//            for (Integer idx : neighbours) {
//                Tile currentNeighbour = tiles.get(idx);
//                if (currentNeighbour.getType() == TileType.LAND && !currentNeighbour.hasAquifer()) {
////                    currentNeighbour.
//                }
//            }
//        }
//        while (currentWaterSize <= waterSize) {
//            Set<Integer> neighbours = sourceTilePolygon.getPolygonNeighbours();
//            for (Integer idx : neighbours) {
//                Tile currentNeighbour = tileSet.get(idx);
//                Polygon neighbourPolygon = currentNeighbour.getPolygon();
//                if (landTiles.contains(currentNeighbour))  {
//                    LakeTile lakeNeighbourTile = new LakeTile(neighbourPolygon);
//                    island.getTiles().update(currentNeighbour, lakeNeighbourTile);
//                    currentLakeSize++;
//                }
//                if (currentLakeSize >= lakeSize || !availableTiles(sourceTile)) {
//                    return;
//                }
//            }
//        }
}

