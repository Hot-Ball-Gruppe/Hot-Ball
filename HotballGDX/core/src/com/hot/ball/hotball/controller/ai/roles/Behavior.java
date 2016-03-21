/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.universe.player.Player;

/**
 *
 * @author Dromlius
 */
public abstract class Behavior {
    public final static Behavior PIRANHA =new Piranha();
    public final static Behavior DISRUPTOR= new Disruptor();
    
    protected Behavior(){}
    
    public abstract Vector action(Player p);
    
    protected Vector goToPlayer(Player p,Player target){
        if(p.getPosition().getDistance(target.getPosition())<2*p.getTackleZoneSize()/3){
            
        }
        return new Vector(1, p.getPosition().angleBetween(target.getPosition()), null);
    }
}
