package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods that all profiles for Soil requires to process
 */
public abstract class SoilUtil implements SoilProfile {

    Island island;

    // max for land tiles
    private Double maxAbsorption = Double.MIN_VALUE;
    // min for land tiles
    private Double minAbsorption = Double.MAX_VALUE;

    /**
     * Takes a set of tiles and associates an absorption value for each tile
     * @param island: container with a set of tiles, borders, and VertexDecorators
     */
    public void process(Island island) {
        this.island = island;
        setSoilProfiles(island);
        standardizeAbsorbances(island);
    }

    /**
     * Iterates through all Tiles and sets absorption level based on the profile
     * @param island
     */
    private void setSoilProfiles(Island island) {
        for (Tile tile : island.getTiles()) {
            if (tile.isOcean()) tile.setAbsorption(0.0);
            else if (tile.hasLake()) {
                Double moisture = (double)tile.getWater().moisture();
                tile.setAbsorption(moisture);
                checkMinMax(moisture);
            }
            else {
                tile.setSoilProfile(getSoilProfile());
                Double absorption = calculateAbsorption(tile);
                checkMinMax(absorption);
                tile.setAbsorption(absorption);
            }
        }
    }

    private void checkMinMax(Double moisture) {
        if (moisture < minAbsorption) minAbsorption = moisture;
        if (moisture > maxAbsorption) maxAbsorption = moisture;
    }

    public abstract SoilProfile getSoilProfile();


    /**
     * Based on the type of tile (lake, river, aquifer), an absorption value will be associated with it
     * @param tile, a tile on the Island
     * @return Double, value of absorption
     */
    private Double calculateAbsorption(Tile tile) {
        double absorption = 0.0;

        for (Tile currentTile: island.getTiles()) {
            // don't get moisture from ocean
            if (currentTile.isOcean()) continue;

            // if the tile is a lake or aquifer, get distance to it then calculate absorbance based on that
            if (currentTile.hasLake() || currentTile.hasAquifer()) {
                double distance = calculateDistance(tile, currentTile);
                absorption += calcLandWaterAbsorption(currentTile, distance);
            }

            if (touchesRiver(currentTile)) {
                for (Border border : currentTile.getBorders()) {
                    if (border.hasRiver()) {
                        double distance = calculateDistance(tile, border);
                        Integer riverMultiplicity = border.getWater().multiplicity();
                        Integer riverMoisture = border.getWater().moisture();
                        absorption += calcRiverAbsorption(distance, riverMultiplicity, riverMoisture);
                    }
                }
            }
        }
        return absorption;
    }

    /**
     * Method used to determine the absorption level for a land water element (lake, aquifer)
     * @param currentTile, tile on the land
     * @param distance, proximity from a land water element
     * @return double, value of absorption
     */
    protected abstract double calcLandWaterAbsorption(Tile currentTile, double distance);

    /**
     * Method used to determine the absorption level for a river element
     * @param distance, proximity to a river
     * @param riverMultiplicity, the multiplicity value for a river
     * @param riverMoisture, moisture value associated with a river
     * @return
     */
    protected abstract double calcRiverAbsorption(double distance, Integer riverMultiplicity, Integer riverMoisture);

    /**
     * Checks if a given land tile contains a border that is a river
     * @param tile, a land tile
     * @return boolean
     */
    protected boolean touchesRiver(Tile tile) {
        List<Tile> neighbours = new ArrayList<>(tile.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList());
        for (Tile neighbour : neighbours) {
            for (Border border : neighbour.getBorders()) {
                if (border.hasRiver()) return true;
            }
        }
        return false;
    }

    /**
     * Gets the distance between the centroids of two tiles
     * @param sourceTile, tile 1
     * @param endTile, tile 2
     * @return double, distance between two tiles
     */
    protected double calculateDistance(Tile sourceTile, Tile endTile) {
        double sourceX = sourceTile.getCentroid().getX();
        double sourceY = sourceTile.getCentroid().getY();
        double endX = endTile.getCentroid().getX();
        double endY = endTile.getCentroid().getY();
        double xDifference = endX - sourceX;
        double yDifference = endY - sourceY;
        return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2));
    }

    /**
     * Gets the distance between a tile and a border
     * @param sourceTile, land tile
     * @param endBorder, border
     * @return
     */
    protected double calculateDistance(Tile sourceTile, Border endBorder) {
        double sourceX = sourceTile.getCentroid().getX();
        double sourceY = sourceTile.getCentroid().getY();
        double v1X = endBorder.getV1().getX();
        double v1Y = endBorder.getV1().getY();
        double v2X = endBorder.getV2().getX();
        double v2Y = endBorder.getV2().getY();
        double midpointX = (v1X + v2X)/2;
        double midpointY = (v1Y + v2Y)/2;
        double xDifference = midpointX - sourceX;
        double yDifference = midpointY - sourceY;
        return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2));
    }

    /**
     * Standardizes absorption values to be between 0 and 100
     * @param island, container for tiles, borders, and vertex decorators
     */
    protected void standardizeAbsorbances(Island island) {
        for (Tile tile : island.getTiles()) {
            if (tile.isOcean()) tile.setAbsorption(0.0);
            else if (tile.hasLake()) tile.setAbsorption(tile.getWater().moisture()*1.0);
            else {
                Double standardized = 100 * (tile.getAbsorption() - minAbsorption) / maxAbsorption;
                tile.setAbsorption(standardized);
            }
        }
    }
}
