package ca.mcmaster.cas.se2aa4.a2.island.Features.Water;

import ca.mcmaster.cas.se2aa4.a2.island.Containers.Island;
import ca.mcmaster.cas.se2aa4.a2.island.Features.Seed;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Border;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.Tile;
import ca.mcmaster.cas.se2aa4.a2.island.Geography.VertexDecorator;
import ca.mcmaster.cas.se2aa4.a2.island.TileType;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static ca.mcmaster.cas.se2aa4.a2.island.TileType.TEST;

public class RiverGenerator implements WaterGenerator {

    Island island;

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

        if (getLowerVertices(spring, getNeighbouringVertices(spring, island.getTiles())).isEmpty()) {
            return false;
        }

        spring.setColor(Color.MAGENTA);

        while (spring != null) {
            // Check all neighbouring vertices for a lower altitude
            List<Tile> sourceNeighbours = source.getNeighbours().stream()
                    .map(id -> island.getTile(id)).toList();
            List<VertexDecorator> springNeighbours = getNeighbouringVertices(spring, island.getTiles());
            spring = riverFlow(spring, springNeighbours);
        }

        return true;
    }

    private VertexDecorator riverFlow(VertexDecorator spring, List<VertexDecorator> springNeighbours) {
        List<VertexDecorator> lowerAltitudeNeighbours = getLowerVertices(spring, springNeighbours);
        VertexDecorator maxValue;
        if (lowerAltitudeNeighbours.isEmpty()) return null;
        else {
            // Choose the highest neighbour from lowest neighbours
            maxValue = lowerAltitudeNeighbours.stream()
                    .max(Comparator.comparing(VertexDecorator::getAltitude)).get();
            Border river = searchForBorder(spring, maxValue);
            if (river != null) {
                river.setWater(new River());
            }
        }
        return maxValue;
    }

    private List<Tile> getLandTiles() {
        List<Tile> landTiles = new ArrayList<>();
        for (Tile tile : island.getTiles()) {
            if (tile.getType() == TileType.LAND) {
                landTiles.add(tile);
            }
        }
        return landTiles;
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
            border.getV1() == lowerAltitude && border.getV2() == spring) {
                return border;
            }
        }
        return null;
    }
}
