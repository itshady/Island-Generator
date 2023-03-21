package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;

import java.util.ArrayList;
import java.util.List;

public abstract class SoilUtil implements SoilProfile {

    Island island;

    // max for land tiles
    private Double maxAbsorption = Double.MIN_VALUE;
    // min for land tiles
    private Double minAbsorption = Double.MAX_VALUE;

    public void process(Island island) {
        this.island = island;
        setSoilProfiles(island);
        standardizeAbsorbances(island);
        for (Tile tile : island.getTiles()) {
            if (tile.hasLake()) System.out.println(tile.getWater().multiplicity() + " " + tile.getAbsorption());
        }
    }

    private void setSoilProfiles(Island island) {
        for (Tile tile : island.getTiles()) {
            if (tile.isOcean()) tile.setAbsorption(0.0);
            else if (tile.hasLake()) tile.setAbsorption((double)tile.getWater().moisture());
            else {
                tile.setSoilProfile(getSoilProfile());
                Double absorption = calculateAbsorption(tile);
                if (absorption < minAbsorption) minAbsorption = absorption;
                if (absorption > maxAbsorption) maxAbsorption = absorption;
                tile.setAbsorption(absorption);
            }
        }
    }

    public abstract SoilProfile getSoilProfile();


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

    protected abstract double calcLandWaterAbsorption(Tile currentTile, double distance);

    protected abstract double calcRiverAbsorption(double distance, Integer riverMultiplicity, Integer riverMoisture);

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

    // calculate distance between two centroids
    protected double calculateDistance(Tile sourceTile, Tile endTile) {
        double sourceX = sourceTile.getCentroid().getX();
        double sourceY = sourceTile.getCentroid().getY();
        double endX = endTile.getCentroid().getX();
        double endY = endTile.getCentroid().getY();
        double xDifference = endX - sourceX;
        double yDifference = endY - sourceY;
        return Math.sqrt(Math.pow(xDifference,2) + Math.pow(yDifference,2));
    }

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

    protected void standardizeAbsorbances(Island island) {
        for (Tile tile : island.getTiles()) {
            if (tile.isOcean()) tile.setAbsorption(0.0);
            else {
                Double standardized = 100 * (tile.getAbsorption() - minAbsorption) / maxAbsorption;
                tile.setAbsorption(standardized);
            }
        }
    }
}
