package ca.mcmaster.cas.se2aa4.a2.island.Features;

import ca.mcmaster.cas.se2aa4.a2.island.UI.Configuration;

import java.util.Random;

public class Seed {
    public static Integer SEED;

    public void process(Configuration config) {
        String input = config.export(Configuration.SEED);
        if (input != null) {
            SEED = Integer.parseInt(input);
        } else SEED = (new Random().nextInt()) & Integer.MAX_VALUE; // gets rid of signed bit so only positive seeds
        System.out.println("Seed: " + SEED);
    }
}
