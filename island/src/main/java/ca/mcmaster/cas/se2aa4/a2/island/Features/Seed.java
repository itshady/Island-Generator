package ca.mcmaster.cas.se2aa4.a2.island.Features;

import java.util.Random;

public class Seed {
    private static Seed instance;
    private Integer seed;
    private Random random;

    private Seed(Integer seed, Random random) {
        this.seed = seed;
        this.random = random;
    }

    public static Seed getInstance() {
        if (instance != null) return instance;
        int seed = (new Random().nextInt()) & Integer.MAX_VALUE; // gets rid of signed bit so only positive seeds
        Random random = new Random(seed);
        instance = new Seed(seed, random);
        return instance;
    }
    public static Seed getInstance(String inputSeed) {
        if (inputSeed == null) return getInstance();
        try {
            if (instance != null) return instance;
            Integer seed = Integer.parseInt(inputSeed);
            Random random = new Random(seed);
            instance = new Seed(seed, random);
            return instance;
        } catch (Exception e) {
            System.out.println("You did not enter a valid seed so a random one was chosen.");
            return getInstance();
        }
    }

    public void printSeed() {
        if (instance == null) throw new IllegalArgumentException("No instance exists. Must create one first.");
        System.out.println("Seed: " + seed);
    }

    public Integer nextInt() {
        return random.nextInt();
    }

    public Integer nextInt(Integer min, Integer max) {
        return random.nextInt(min, max);
    }

    public Integer nextInt(Integer max) {
        return random.nextInt(max);
    }

    public boolean nextBoolean() {
        return random.nextBoolean();
    }
}
