/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

import com.hot.ball.hotball.universe.court.Basket;
import com.hot.ball.hotball.universe.court.Court;
import java.util.Objects;

/**
 *
 * @author Inga
 */
public class Team {

    public static void generate(Player[] blueTeam, Player[] redTeam, Basket blueBasket, Basket redBasket) {
        BLUE = new Team(TeamColor.Blue, blueTeam, redBasket);
        RED = new Team(TeamColor.Red, redTeam, blueBasket);
        BLUE.setOpponent(RED);
        RED.setOpponent(BLUE);
    }

    public static Team BLUE;
    public static Team RED;

    private final TeamColor color;
    private final Basket attacking;
    private Team opponent;

    private final Player[] members;

    @SuppressWarnings("LeakingThisInConstructor")
    private Team(TeamColor color, Player[] team, Basket attacking) {
        this.color = color;
        this.attacking = attacking;
        attacking.setAttacking(this);
        members = team;
        for(Player p:members){
            p.setTeam(this);
        }
        reset();
    }

    public TeamColor getColor() {
        return color;
    }

    public Player[] getMembers() {
        return members;
    }

    public Team getOpponent() {
        return opponent;
    }

    private void setOpponent(Team opponent) {
        this.opponent = opponent;
    }

    public Basket getAttacking() {
        return attacking;
    }

    public boolean isMember(Player p) {
        for (Player member : members) {
            if (member.equals(p)) {
                return true;
            }
        }
        return false;
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

    public final void reset() {
        int x = (color == TeamColor.Blue) ? 70 : Court.COURT_WIDTH - 70;
        for (int i = 0; i < members.length; i++) {
            members[i].getPosition().setX(x);
            members[i].getPosition().setY((i+1)*80);
        }
    }

}
