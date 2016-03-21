/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.Controlled;
import com.hot.ball.hotball.universe.player.Player;

/**
 *
 * @author Inga
 */
public class Disruptor extends Behavior {

    @Override
    public Vector action(Player p) {
        Player ballCarrier = ((Controlled) Ball.get().getState()).getBallCarrier();

        Player closestOpponentToBC = null;
        double closestDist = Double.POSITIVE_INFINITY;

        for (Player opponent : p.getTeam().getOpponent().getMembers()) {
            double dist = ballCarrier.getPosition().getDistance(opponent.getPosition());
            if (dist < closestDist) {
                dist = closestDist;
                closestOpponentToBC = opponent;
            }
        }

        if (closestDist<closestOpponentToBC.getTackleZoneSize()) { //BC in trouble
            return goToPlayer(p, ballCarrier);
        } else {    //Disrupt
            return goToPlayer(p, closestOpponentToBC);
        }
    }

}
