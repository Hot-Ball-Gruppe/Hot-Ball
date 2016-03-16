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
        if ((Ball.get().getState() instanceof InAir)
                && !player.equals(((InAir) Ball.get().getState()).getThrower())) {
            return new Vector(/*Math.min(500, player.getPosition().getDistance(Ball.get().getPosition()))*/1, player.getPosition().angleBetween(Ball.get().getPosition()), null);
        } else {
            return Vector.NULL_VECTOR;
        //    return new Vector(0.5, player.getCurrentVelocity().getTheta()+0.2, null);
        }
    }

}
