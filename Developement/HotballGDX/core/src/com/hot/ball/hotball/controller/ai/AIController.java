/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai;

import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.controller.ai.roles.BallCarrierAI;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.controller.Controller;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.InAir;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Dromlius
 */
public class AIController implements Controller {

    /* @Override
     public double getFacing(Player player) {
     return player.getCurrentVelocity().getTheta();
     }*/
    private Position order;

    public void setOrder(Position order) {
        this.order = order;
    }

    public Position getOrder() {
        return order;
    }

    @Override
    public Vector getMoveVector(Player player) {

        if (order != null) {
            if (player.getPosition().getDistance(order) < player.getSize()) {
                order = null;
            } else {
                return player.getRole().getOffensiveBehavior().goTo(player, order);
            }
        }

        if (Ball.get().getState() instanceof InAir) {
            InAir inAir = (InAir) Ball.get().getState();
            if (!player.equals(inAir.getThrower())) {
                return new Vector(1, player.getPosition().angleBetween(Ball.get().getPosition()), null);
            } else {
                return Vector.NULL_VECTOR;
            }
        } else {
            Player ballCarrier = Ball.get().getBallCarrier();
            Team controllingTeam = ballCarrier.getTeam();
            if (player.getTeam().equals(controllingTeam)) {
                if (player.equals(ballCarrier)) {
                    return BallCarrierAI.get().action(player);
                } else {
                    return player.getRole().getOffensiveBehavior().action(player);
                }
            } else {
                return player.getRole().getDefensiveBehavior().action(player);
            }
        }

    }

}
