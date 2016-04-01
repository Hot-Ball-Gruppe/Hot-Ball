/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai;

/**
 *
 * @author Dromlius
 */
public enum AI_Difficulty {
    EASY(0.6),MEDIUM(1),HARD(1.3);
    
    private static AI_Difficulty difficulty= MEDIUM;
    
    private final double speedFactor;

    private AI_Difficulty(double speedFactor) {
        this.speedFactor = speedFactor;
    }

    public double getSpeedFactor() {
        return speedFactor;
    }

    public static AI_Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(AI_Difficulty difficulty) {
        AI_Difficulty.difficulty = difficulty;
    }
}
