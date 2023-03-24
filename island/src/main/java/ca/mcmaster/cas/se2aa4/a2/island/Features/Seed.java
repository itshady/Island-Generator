package ca.mcmaster.cas.se2aa4.a2.island.Features;

import java.util.Random;

public class Seed {
    private static Integer SEED;
    private static Random random;

    public void process(String seed) {
        try {
            if (seed != null) {
                SEED = Integer.parseInt(seed);
            } else SEED = (new Random().nextInt()) & Integer.MAX_VALUE; // gets rid of signed bit so only positive seeds
            System.out.println("Seed: " + SEED);
            random = new Random(SEED);
        } catch (Exception e) {
            System.out.println("You did not enter a valid seed so a random one was chosen.");
            SEED = (new Random().nextInt()) & Integer.MAX_VALUE;
            random = new Random(SEED);
        }
    }

    public static Integer nextInt() {
        return random.nextInt();
    }

    public static Integer nextInt(Integer min, Integer max) {
        return random.nextInt(min, max);
    }

    public static Integer nextInt(Integer max) {
        return random.nextInt(max);
    }

    public static boolean nextBoolean() {
        return random.nextBoolean();
    }
}
