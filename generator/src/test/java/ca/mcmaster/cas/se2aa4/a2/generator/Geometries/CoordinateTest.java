package ca.mcmaster.cas.se2aa4.a2.generator.Geometries;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void testEquals() {
        // precision is to 0.01, so this shouldn't be equals
        Coordinate coordinate = new Coordinate(1.0,1.0);
        Coordinate coordinate_dup = new Coordinate(1.001,1.001);
        assertEquals(coordinate, coordinate_dup);

        // this should be equals
        coordinate = new Coordinate(1.0,1.0);
        coordinate_dup = new Coordinate(1.01,1.01);
        assertNotEquals(coordinate, coordinate_dup);
    }
}