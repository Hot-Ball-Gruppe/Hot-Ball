/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help.math;

import java.util.Random;

/**
 *
 * @author Dromlius
 */
public class Dice {

    private static Dice singleton;

    public static Dice get() {
        if (singleton == null) {
            singleton = new Dice();
        }
        return singleton;
    }

    private long seed = 0;//(long)(Math.random()*100);
    private Random random = new Random(seed);

    public double getDouble() {
        return random.nextDouble();
    }

    public double getGaussDouble() {
        return getGaussDouble(1);
    }

    public double getGaussDouble(double factor) {
        double rand = random.nextDouble() - random.nextDouble();
        rand = Math.signum(rand) * Math.pow(Math.abs(rand), factor);
        return (rand + 1) / 2;
    }

    public double getInvertedGaussDouble() {
        return getInvertedGaussDouble(1);
    }

    public double getInvertedGaussDouble(double factor) {
        double rand = random.nextDouble() - random.nextDouble();
        rand = Math.signum(rand) * (1 - Math.pow(Math.abs(rand), factor));
        return (rand + 1) / 2;
    }

    public long getSeed() {
        return seed;
    }

    public void randomize() {
        seed = (long) (Math.random() * 100);
        random.setSeed(seed);
    }

    public void inc() {
        seed++;
        random.setSeed(seed);
    }

    public void dec() {
        seed--;
        random.setSeed(seed);
    }

    public void reset() {
        random = new Random(seed);
    }

    public void setSeed(long seed) {
        this.seed = seed;
        random = new Random(seed);
    }
}
