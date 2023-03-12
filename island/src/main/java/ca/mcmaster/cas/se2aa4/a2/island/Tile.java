package ca.mcmaster.cas.se2aa4.a2.island;

import Geometries.Segment;

import java.util.List;

public class Tile extends Geometries.Polygon {
    /**
     * Creators: Overloaded constructors to support different input values
     * Effects: Makes a new Polygon, generates a color, and the segments that makes up the polygon
     *
     * @param segments : A list of segments that makes the polygon
     */
    public Tile(List<Segment> segments) {
        super(segments);
    }
}
