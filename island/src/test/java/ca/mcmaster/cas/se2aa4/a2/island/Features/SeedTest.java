package ca.mcmaster.cas.se2aa4.a2.island.Features;

import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SeedTest {
    @Test
    public void ReproducibilityTest() {
        // this is a known seed that should always return that number on first nextInt call
        new Seed().process("1");
        assertEquals(-1155869325, Seed.nextInt());

        // there is a basically 0% chance it gets that number
        // This tests that wrong input give random seed
        new Seed().process("1.");
        assertNotEquals(-1155869325, Seed.nextInt());
    }
}