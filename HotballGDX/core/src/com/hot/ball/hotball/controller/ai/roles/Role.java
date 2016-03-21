/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

/**
 *
 * @author Dromlius
 */
public enum Role {
    Aggressive(Behavior.DISRUPTOR,Behavior.PIRANHA),Balanced(Behavior.STUPID,Behavior.MARKING),Defensive(null,null);
    
    
    private final Behavior offensiveBehavior;
    private final Behavior defensiveBehavior;

    private Role(Behavior offensiveBehavior, Behavior defensiveBehavior) {
        this.offensiveBehavior = offensiveBehavior;
        this.defensiveBehavior = defensiveBehavior;
    }

    public Behavior getDefensiveBehavior() {
        return defensiveBehavior;
    }

    public Behavior getOffensiveBehavior() {
        return offensiveBehavior;
    }    
}
