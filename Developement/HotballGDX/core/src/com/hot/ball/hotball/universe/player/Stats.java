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
    
    private final double speed_base = 1;
    private final double tackle_base = 0.8;
    private final double power_base = 0.8;
    
    private final double speed_factor = 0.5;
    private final double tackle_factor = 0.6;
    private final double power_factor = 1.05;

    private final double hit_chance_per_power = 28;
    
    public Stats(int speed, int tackle, int power) {
    	
        double total = speed + tackle + power;
        if (total == 0) {
        	speed = 1;
        	tackle = 1;
        	power = 1;
        	total = 3;
    	}
        this.speed = speed_base + (speed * speed_factor) / total ;
        this.tackle = tackle_base + (tackle * tackle_factor) / total;
        this.power = power_base + (power * power_factor) / total;
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
    
    public double getChance() {
    	return (power - power_base) * hit_chance_per_power;
    }
}

