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
     * Creator: Takes in Double values as the coordinates, and converts it to its integer form
     * @param x: Double
     * @param y: Double
     */
    public Coordinate(Double x, Double y) {
        this.x = (int)(x/precision);
        this.y = (int)(y/precision);
    }

    /**
     * Creator: Takes in Integer values as the coordinates and converts it to its new precision
     * @param x: Integer
     * @param y: Integer
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
     * Coordinates are equal if their x and y values are equal to a certain precision
     * @param o: Any object o to compare to
     * @return boolean: True if they are equal, false if they are not
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
