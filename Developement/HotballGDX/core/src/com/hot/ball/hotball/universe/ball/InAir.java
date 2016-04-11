/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.ball;

import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;
import java.util.Stack;

/**
 *
 * @author Dromlius
 */
public class InAir implements BallState {

    private final int id;
    
    private Player thrower;
    private final double chance;

    public InAir(Player thrower, Vector direction, double chance) {
        id = Ball.BALL_ID++;
        this.thrower = thrower;
        this.chance = chance;
        Ball.get().setCurrentVelocity(direction);
    }

    private double airTime = 0;

    @Override
    public void action(double timeDiff) {
        airTime += timeDiff;
        int redTZ = 0;
        int blueTZ = 0;
        boolean inSafeZone = false;
        Stack<Player> possibleCatchers = new Stack<>();
        for (Zone z : Ball.get().getInterferingZones()) {
            if ((z instanceof TackleZone)) {
                TackleZone tz = (TackleZone) z;
                Player tackler = tz.getPlayer();
                if (tackler.equals(thrower)) {
                    inSafeZone = true;
                   continue;
                }
                if (Team.BLUE.isMember(tackler)) {
                    blueTZ++;
                    possibleCatchers.add(tackler);
                }

                if (Team.RED.isMember(tackler)) {
                    redTZ++;
                    possibleCatchers.add(tackler);
                }
            }
        }
        if (possibleCatchers.size() > 0) {
            Player closestCatcher = null;
            double bestCatchDist = Double.POSITIVE_INFINITY;
            for (Player catcher : possibleCatchers) {
                if (inSafeZone && !catcher.getTeam().isMember(thrower)) {
                    continue;
                }
                if ((Team.RED.isMember(catcher) && redTZ >= blueTZ) || (Team.BLUE.isMember(catcher) && redTZ <= blueTZ)) {
                    double catchDistance = catcher.getPosition().getDistance(Ball.get().getPosition());
                    if (catchDistance < bestCatchDist) {
                        closestCatcher = catcher;
                        bestCatchDist = catchDistance;
                    }
                }
            }
            if (closestCatcher != null) {
                Ball.get().setState(new Controlled(closestCatcher));
                Ball.get().action(timeDiff);
                return;
            }
        }
        if (Ball.get().getPosition().getX() < 0 || Ball.get().getPosition().getX() > Court.COURT_WIDTH) {
            Vector vector = new Vector(Ball.get().getCurrentVelocity());
            vector.multiply(1d / Ball.get().getCurrentMaxSpeed());
            Ball.get().accelerate(timeDiff, vector);
        } else {
            Ball.get().accelerate(timeDiff, Vector.NULL_VECTOR);
        }
    }

    public double getAirTime() {
        return airTime;
    }

    public Player getThrower() {
        return thrower;
    }

    public void setThrower(Player thrower) {
        this.thrower = thrower;
    }

    public double getChance() {
        return chance;
    }

    @Override
    public int getID() {
        return id;
    }
    
    
}
