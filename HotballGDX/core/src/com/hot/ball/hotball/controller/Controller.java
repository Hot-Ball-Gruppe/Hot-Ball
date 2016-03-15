/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotball.controller;

import help.math.Vector;
import hotball.universe.player.Player;

/**
 *
 * @author Dromlius
 */
public interface Controller{

   // public double getFacing(Player player) ;
    
    default double getFacing(Player player) {
        return player.getCurrentVelocity().getTheta();
    }

    public Vector getMoveVector(Player player);

   
    
}
