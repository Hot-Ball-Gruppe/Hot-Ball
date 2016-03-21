/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

/**
 *
 * @author Dromlius
 */
public class Stats {

    private final double speed;
    private final double tackle;
    private final double power;

    public Stats(int speed, int tackle, int power) {
        double total = speed + tackle + power;
        this.speed = 1 + speed / total;
        this.tackle = 1 + tackle / total;
        this.power = 1 + power / total;
    }

    public double getPower() {
        return power;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTackle() {
        return tackle;
    }
}
