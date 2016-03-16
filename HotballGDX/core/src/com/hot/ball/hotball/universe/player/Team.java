/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Inga
 */
public class Team {
    private final TeamColor color;

    private final Collection<Player> members;
    
    public Team(TeamColor color) {
        this.color = color;
        members = new ArrayList<>();
    }

    public TeamColor getColor() {
        return color;
    }

    public Collection<Player> getMembers() {
        return members;
    }
    
    
}
