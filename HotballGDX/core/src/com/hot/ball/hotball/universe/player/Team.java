/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

import com.hot.ball.hotball.universe.court.Basket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Inga
 */
public class Team {
    public static final  Team BLUE = new Team(TeamColor.Blue);
    public static final  Team RED = new Team(TeamColor.Red);
    
    static{
        BLUE.setOpponent(RED);
        RED.setOpponent(BLUE);
    }
    private final TeamColor color;
    private Basket attacking;
    private Team opponent;
    
    private final List<Player> members;
    
    private Team(TeamColor color) {
        this.color = color;
        members = new ArrayList<>();
    }

    public TeamColor getColor() {
        return color;
    }

    public List<Player> getMembers() {
        return members;
    }

    public Team getOpponent() {
        return opponent;
    }

    public void setOpponent(Team opponent) {
        this.opponent = opponent;
    }

    public Basket getAttacking() {
        return attacking;
    }

    public void setAttacking(Basket attacking) {
        this.attacking = attacking;
    }
    
    public boolean isMember(Player p){
        return getMembers().contains(p);
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.color);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Team other = (Team) obj;
        if (this.color != other.color) {
            return false;
        }
        return true;
    }
    
    
}
