/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

import java.awt.Color;


/**
 *
 * @author Dromlius
 */
public enum TeamColor {
    Red(Color.RED),Blue(Color.BLUE);
    
    private final Color color;
    private final Color transColor;
    
    private TeamColor(Color color) {
        this.color = color;
        transColor = new Color(color.getRed(), color.getGreen(), color.getBlue(), 100);
    }

    public Color getColor() {
        return color;
    }
    
    public Color getTransColor(){
        return transColor;
    }
    
}
