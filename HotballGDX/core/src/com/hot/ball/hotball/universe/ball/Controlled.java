/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.ball;

import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.AIController;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.TeamColor;

/**
 *
 * @author Inga
 */
public class Controlled implements BallState {

    private final Player ballCarrier;

    public Controlled(Player ballCarrier) {
        this.ballCarrier = ballCarrier;
        if (ballCarrier.getTeam().getColor() == TeamColor.Blue && !ballCarrier.isHuman()) {
            Player.humanPlayer.setController(new AIController());
            ballCarrier.setController(HumanController.get());
        }
    }

    public Player getBallCarrier() {
        return ballCarrier;
    }

    @Override
    public void action(double timeDiff) {
        Ball.get().getPosition().set(ballCarrier.getPosition());
    }

}
