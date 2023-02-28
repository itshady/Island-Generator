package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import java.util.Objects;

/**
 * Mutable Coordinate that holds and x and y value on a grid.
 * x and y are integers which represent (the double value) divided by the precision.
 */
public class Coordinate {
    Integer x;
    Integer y;
    public static double precision;

    /**
     * Creator: Takes in Double values as input
     * @param x
     * @param y
     */
    public Coordinate(Double x, Double y) {
        this.x = (int)(x/precision);
        this.y = (int)(y/precision);
    }

    /**
     * Creator: Takes in Integer values as input
     * @param x Integer
     * @param y Integer
     */
    public Coordinate(Integer x, Integer y) {
        this.x = (int)(x/precision);
        this.y = (int)(y/precision);
    }

    public Double getX() {
        return x*precision;
    }

    public Double getY() {
        return y*precision;
    }

    /**
     * Overridden the Equals method to allow for proper comparison of Coordinates
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    /**
     * Overridden the HashCode method to prevent collisions (maintain unique Coordinates)
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
