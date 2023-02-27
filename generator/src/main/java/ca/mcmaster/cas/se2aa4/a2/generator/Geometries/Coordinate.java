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

    public Coordinate(Double x, Double y) {
        this.x = (int)(x/precision);
        this.y = (int)(y/precision);
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(x, that.x) && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
