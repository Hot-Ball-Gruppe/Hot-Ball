/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai;

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
    @Override
    public Vector getMoveVector(Player player) {
        if (Ball.get().getState() instanceof InAir) {
            InAir inAir = (InAir) Ball.get().getState();
            if (!player.equals(inAir.getThrower())) {
                return new Vector(1, player.getPosition().angleBetween(Ball.get().getPosition()), null);
            } else {
                return Vector.NULL_VECTOR;
            }
        } else {
            Player ballCarrier =Ball.get().getBallCarrier();
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
        /*
         Position.DoublePosition position = player.getPosition();
         Vector aiMoveVector = getAIMoveVector(player);
         //    aiMoveVector.addVector(new Vector(force/(position.getX()*position.getX()), 0));
         //   aiMoveVector.addVector(new Vector( 0,force/(position.getY())));
         //   aiMoveVector.addVector(new Vector( force/((1200-position.getX())*(1200-position.getX())),0));
         //   aiMoveVector.addVector(new Vector( 0,force/((600-position.getY()))));
         return aiMoveVector.setLength(Math.min(1, aiMoveVector.getLength()));
         }

         private Vector getAIMoveVector(Player player) {
         if (Ball.get().isControlledBy(player)) {
         if (player.getChanceToHit() > 0.4 + Math.random() * 0.3) {
         Ball.get().throwBall(player.getTeam().getAttacking().getPosition(), player.getThrowPower());
         } else {
         return new Vector(1, player.getPosition().angleBetween(player.getTeam().getAttacking().getPosition()), null);
         }
         //  return ballCarrierAI(player);
         }

         if (Ball.get().getState() instanceof InAir) {
         if (!player.equals(((InAir) Ball.get().getState()).getThrower())) {
         return new Vector(/*Math.min(500, player.getPosition().getDistance(Ball.get().getPosition()))*//*1, player.getPosition().angleBetween(Ball.get().getPosition()), null);
         }
         } else {
         Player ballCarrier = ((Controlled) Ball.get().getState()).getBallCarrier();
         if (player.equals(ballCarrier)) {
         Player closestOpponent = null;
         double closestDist = Double.POSITIVE_INFINITY;
         for (Player opponent : player.getTeam().getOpponent().getMembers()) {
         double distance = player.getPosition().getDistance(opponent.getPosition());
         if (distance < closestDist) {
         closestOpponent = opponent;
         closestDist = distance;
         }
         }
         if (closestDist < 300) {
         return new Vector(1, closestOpponent.getPosition().angleBetween(player.getPosition()), null);
         }
         } else if (!ballCarrier.getTeam().isMember(player)) {
         return new Vector(1, player.getPosition().angleBetween(ballCarrier.getPosition()), null);
         }
         }
         return new Vector(0.6, player.getCurrentVelocity().getTheta() + 0.1, null);
         */

    }

    private Vector ballCarrierAI(Player player) {
        if (player.getChanceToHit() > 0.4 + Math.random() * 0.3) {
            Ball.get().throwBall(player.getTeam().getAttacking().getPosition(), player.getThrowPower());
            return Vector.NULL_VECTOR;
        } else {
            return new Vector(1, player.getPosition().angleBetween(player.getTeam().getAttacking().getPosition()), null);
        }
    }

}
