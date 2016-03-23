/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.analysis;

import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 *
 * @author Dromlius
 */
public class VoronoiArea {

    private final Position.FinalPosition center;
    private final Set<VoronoiArea> connected;

    private final Map<Team, Rating> ratings;

    private final Set<Player> occupants;

    public VoronoiArea(Position.FinalPosition center) {
        this.center = center;
        this.connected = new HashSet<>();
        this.ratings = new HashMap<>();
        this.occupants = new HashSet<>();
    }

    public Position.FinalPosition getCenter() {
        return center;
    }

    public Set<Player> getOccupants() {
        return occupants;
    }

    public void addOccupant(Player p) {
        occupants.add(p);
    }

    public void reset() {
        occupants.clear();
    }

    public Set<VoronoiArea> getConnected() {
        return connected;
    }

    public void addConnection(VoronoiArea va) {
        connected.add(va);
    }

    public void setAtkRating(int atk) {
        Team team;
        if (center.getX() < Court.COURT_WIDTH / 2 - 10) {
            team = Court.get().getLeftBasket().getAttacking();
            Rating r = ratings.get(team);
            if (r == null) {
                r = new Rating();
                ratings.put(team, r);
            }
            r.setAtk(atk);
        }

        if (center.getX() > Court.COURT_WIDTH / 2 + 10) {
            
            team = Court.get().getRightBasket().getAttacking();
            Rating r = ratings.get(team);
            if (r == null) {
                r = new Rating();
                ratings.put(team, r);
            }
            r.setAtk(atk);
        }
    }

    public void setMobRating(int mob) {
        Rating bluerating = ratings.get(Team.BLUE);
        if (bluerating == null) {
            bluerating = new Rating();
            ratings.put(Team.BLUE, bluerating);
        }
        bluerating.setMob(mob);

        Rating redrating = ratings.get(Team.RED);
        if (redrating == null) {
            redrating = new Rating();
            ratings.put(Team.RED, redrating);
        }
        redrating.setMob(mob);
    }

    public void setDefRating(int def) {
        Team team;
        if (center.getX() < Court.COURT_WIDTH / 2 - 10) {
            team = Court.get().getRightBasket().getAttacking();
            Rating r = ratings.get(team);
            if (r == null) {
                r = new Rating();
                ratings.put(team, r);
            }
            r.setDef(def);
        }

        if (center.getX() > Court.COURT_WIDTH / 2 + 10) {
            team = Court.get().getLeftBasket().getAttacking();
            Rating r = ratings.get(team);
            if (r == null) {
                r = new Rating();
                ratings.put(team, r);
            }
            r.setDef(def);
        }
    }

    public int getAttackRating(Team team) {
        return ratings.get(team).getAtk();
    }

    public int getDefenseRating(Team team) {
        return ratings.get(team).getDef();
    }

    public int getMobilityRating(Team team) {
        return ratings.get(team).getMob();
    }

    private class Rating {

        private int atk, def, mob;

        public void setAtk(int atk) {
            this.atk = atk;
        }

        public void setDef(int def) {
            this.def = def;
        }

        public void setMob(int mob) {
            this.mob = mob;
        }

        public int getAtk() {
            return atk;
        }

        public int getDef() {
            return def;
        }

        public int getMob() {
            return mob;
        }

        @Override
        public String toString() {
            return "ATK: "+getAtk();
        }
        
        
    }


    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.center);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VoronoiArea other = (VoronoiArea) obj;
        if (!Objects.equals(this.center, other.center)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return center.toString()+": RED:"+getAttackRating(Team.RED)+", BLUE:"+getAttackRating(Team.BLUE);
    }
}
