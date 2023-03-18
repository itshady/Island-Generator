package ca.mcmaster.cas.se2aa4.a2.island.Features.Soil;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SoilUtil implements SoilProfile {

    Island island;

    public static Double maxAbsorption = 0.0;

    public void process(Island island) {
        this.island = island;
        setSoilProfiles();
    }

    public abstract Double getAbsorptionRate();

    public abstract SoilProfile getSoilProfile();

    public abstract Color getSoilColor();

    private Double calculateAbsorption(Tile tile, Double absorptionRate) {

        double absorption = 0.0;

        for (Tile currentTile: island.getTiles()) {
            if (currentTile.getType() == TileType.OCEAN) continue;
            if (currentTile.hasLake() || currentTile.hasAquifer()) {
                double distance = calculateDistance(tile, currentTile);
                absorption += (currentTile.getWater().moisture() * Math.pow(absorptionRate,distance/50));
            }

            if (touchesRiver(currentTile)) {
                for (Border border : currentTile.getBorders()) {
                    if (border.hasRiver()) {
                        double distance = calculateDistance(tile, border);
                        Integer riverMultiplicity = border.getWater().multiplicity();
                        Integer riverMoisture = border.getWater().moisture();
                        absorption += (riverMoisture*riverMultiplicity*(Math.pow((absorptionRate), distance/50)));
                    }
                }
            }

            if (touchesOcean(currentTile)) {
                double distance = calculateDistance(tile, currentTile);
                absorption += (100 * Math.pow(absorptionRate,distance/50));
            }
        }
        return absorption;
    }

    private void setSoilProfiles() {
        for (Tile tile : island.getTiles()) {
            tile.setSoilProfile(getSoilProfile());
            Double absorption = calculateAbsorption(tile, getAbsorptionRate());
            if (absorption > maxAbsorption) maxAbsorption = absorption;
            tile.setAbsorption(absorption);
            tile.setColor(getSoilColor());
            System.out.println(tile.getAbsorption());
        }
    }

    protected boolean touchesOcean(Tile tile) {
        List<Tile> neighbours = new ArrayList<>(tile.getNeighbours().stream()
                .map(id -> island.getTile(id)).toList());
        for (Tile neighbour : neighbours) {
            if (neighbour.isOcean()) return true;
        }
        return false;
    }

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
}
