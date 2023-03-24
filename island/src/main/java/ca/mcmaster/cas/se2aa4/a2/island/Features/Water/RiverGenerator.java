package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Island.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;

import java.util.*;
import java.util.List;

public class RiverGenerator implements WaterGenerator {

    Island island;
    List<List<River>> rivers = new ArrayList<>();

    @Override
    public void process(Island island, Integer nums) {
        this.island = island;
        // Iterate n times (user specified)
        // generateWater method
        int i = 0;
        int numOfTiles = island.getTiles().size();

        // try to generate water on every tile until you've checked them all
        while (i < nums && numOfTiles > 0) {
            if (generateWater()) i++;
            numOfTiles--;
        }
    }

    // Generates a single river
    public boolean generateWater() {

        VertexDecorator spring;

        // Randomly select a tile
        List<Tile> landTiles = getLandTiles();
        Tile source = landTiles.get(Seed.nextInt(landTiles.size()));

        // Randomly select a border
        Border springBorder = source.getBorders().get(Seed.nextInt(source.getBorders().size()));

        // Check to see which vertex is higher
        if (springBorder.getV1().getAltitude() >= springBorder.getV2().getAltitude()) {
            spring = springBorder.getV1();
        } else {
            spring = springBorder.getV2();
        }
        // Get neighbouring vertices and check that there are no rivers in the borders from current spring to neighbouring vertices
        if (isInRiver(spring)) return false;
        if (getLowerVertices(spring, getNeighbouringVertices(spring, island.getTiles())).isEmpty()) {
            return false;
        }

        spring.setSpring(true);
        VertexDecorator previousSpring = spring;
        List<River> riverList = new ArrayList<>();
        while (spring != null) {
            // Check all neighbouring vertices for a lower altitude
            List<VertexDecorator> springNeighbours = getNeighbouringVertices(spring, island.getTiles());
            previousSpring = spring;
            spring = riverFlow(spring, springNeighbours, riverList);

        }
        rivers.add(riverList);

        //If river doesn't end in a lake or an ocean
        if (!decoratorOfLandWater(previousSpring)) {
            // Check the shared tiles with that final vertex and search for the one that doesn't contain a river border.
            for (Tile tile : getNeighbouringTiles(previousSpring)) {
                // Create a lake at that tile.
                if (!tileContainsRiverBorder(tile)) tile.setWater(new Lake());
            }
        }
        return true;
    }

    private boolean isInRiver(VertexDecorator spring) {
        List<VertexDecorator> neighbouringVertices = getNeighbouringVertices(spring, island.getTiles());

        for (Tile tile: island.getTiles()) {
            // Iterate through the border
            for (Border border: tile.getBorders()) {
                if ((spring == border.getV1() && neighbouringVertices.contains(border.getV2())) ||
                        (spring == border.getV2() && neighbouringVertices.contains(border.getV1()))) {
                    if (border.hasRiver()) return true;
                }
            }
        }
        return false;
    }

    private boolean tileContainsRiverBorder(Tile tile) {
        for (Border border : tile.getBorders()) {
            if (border.hasRiver()) return true;
        }
        return false;
    }

    private List<Tile> getNeighbouringTiles(VertexDecorator source) {
        Set<Tile> neighbouringTiles = new HashSet<>();
        for (Tile tile : island.getTiles()) {
            for (Border border : tile.getBorders()) {
                if (border.getV1() == source || border.getV2() == source) neighbouringTiles.add(tile);
            }
        }
        return new ArrayList<>(neighbouringTiles);
    }

    private VertexDecorator riverFlow(VertexDecorator spring, List<VertexDecorator> springNeighbours, List<River> currentRiver) {
        List<VertexDecorator> lowerAltitudeNeighbours = getLowerVertices(spring, springNeighbours);
        VertexDecorator maxValue;
        if (lowerAltitudeNeighbours.isEmpty()) return null;
        else {
            // Choose the highest neighbour from lowest neighbours
            maxValue = lowerAltitudeNeighbours.stream()
                    .max(Comparator.comparing(VertexDecorator::getAltitude)).get();
            Border border = searchForBorder(spring, maxValue);
            if (borderOfLandWater(border)) return null;
            assert border != null;
            if (border.hasRiver()) {
                Integer multiplicity = border.getWater().multiplicity();
                ((Water) border.getWater()).setMultiplicity(multiplicity + 1);
            } else {
                River newRiver = new River();
                border.setWater(newRiver);
                currentRiver.add(newRiver);
            }


        }
        return maxValue;
    }

    private List<Tile> getLandTiles() {
        List<Tile> landTiles = new ArrayList<>();
        for (Tile tile : island.getTiles()) {
            if (tile.isLand()) {
                landTiles.add(tile);
            }
        }
        return landTiles;
    }

    private boolean borderOfLandWater(Border border) {
        for (Tile tile : island.getTiles()) {
            if (tile.getBorders().contains(border) && (tile.isOcean() || tile.hasLake())) return true;
        }
        return false;
    }

    private boolean decoratorOfLandWater(VertexDecorator vertexDecorator) {
        for (Tile tile : island.getTiles()) {
            for (Border border : tile.getBorders()){
                if ((border.getV1() == vertexDecorator || border.getV2() == vertexDecorator) && (tile.isOcean() || tile.hasLake())) return true;
            }

        }
        return false;
    }

    private List<VertexDecorator> getNeighbouringVertices(VertexDecorator spring, List<Tile> tilesToCheck) {
        Set<VertexDecorator> neighbours = new HashSet<>();

        for (Tile tile: tilesToCheck) {
            // Iterate through the border
            for (Border border: tile.getBorders()) {
                if (border.getV1() == spring) {
                    neighbours.add(border.getV2());
                } else if (border.getV2() == spring) {
                    neighbours.add(border.getV1());
                }
            }
        }

        return new ArrayList<>(neighbours);
    }

    private List<VertexDecorator> getLowerVertices(VertexDecorator spring, List<VertexDecorator> neighbours) {
        List<VertexDecorator> lowerAltitudes = new ArrayList<>();

        for (VertexDecorator neighbour: neighbours) {
            if (neighbour.getAltitude() < spring.getAltitude()) {
                lowerAltitudes.add(neighbour);
            }
        }

        return lowerAltitudes;
    }

    private Border searchForBorder(VertexDecorator spring, VertexDecorator lowerAltitude) {
        List<Border> borders = island.getBorders();

        for (Border border: borders) {
            if ((border.getV1() == spring && border.getV2() == lowerAltitude) ||
                    (border.getV1() == lowerAltitude && border.getV2() == spring)) {
                return border;
            }
        }
        return null;
    }
}
