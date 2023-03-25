package ca.mcmaster.cas.se2aa4.a2.island.Features;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeedTest {
    @Test
    public void ReproducibilityTest() {
        // this is a known seed that should always return that number on first nextInt call
        Seed seed = Seed.getInstance("1");
        assertEquals(-1155869325, seed.nextInt());

        // also known number that will be returned on second nextInt call for seed 1
        Seed seed2 = Seed.getInstance();
        assertEquals(431529176, seed2.nextInt());
    }
}