/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.ball;

import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.TeamColor;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;
import java.util.Stack;

/**
 *
 * @author Dromlius
 */
public class InAir implements BallState {

    private final Player thrower;
    // private double power;

    public InAir(Player thrower, Vector direction) {
        this.thrower = thrower;
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
                if (tackler.getTeam().getColor() == TeamColor.Blue) {
                    blueTZ++;
                    possibleCatchers.add(tackler);
                }

                if (tackler.getTeam().getColor() == TeamColor.Red) {
                    redTZ++;
                    possibleCatchers.add(tackler);
                }
            }
        }
        if (possibleCatchers.size() > 0) {
            Player closestCatcher = null;
            double bestCatchDist = Double.POSITIVE_INFINITY;
            for (Player catcher : possibleCatchers) {
                if (inSafeZone && catcher.getTeam().getColor() != thrower.getTeam().getColor()) {
                    continue;
                }
                if ((catcher.getTeam().getColor() == TeamColor.Red && redTZ >= blueTZ) || (catcher.getTeam().getColor() == TeamColor.Blue && redTZ <= blueTZ)) {
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
        Ball.get().accelerate(timeDiff, Vector.NULL_VECTOR);
    }

    public double getAirTime() {
        return airTime;
    }

    public Player getThrower() {
        return thrower;
    }
}
