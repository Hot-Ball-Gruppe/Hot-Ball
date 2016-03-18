/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.ball;

import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.AIController;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Inga
 */
public class Controlled implements BallState {

    private final int id;

    private final Player ballCarrier;

    public Controlled(Player ballCarrier) {
        id = Ball.BALL_ID++;
        this.ballCarrier = ballCarrier;
        if (Team.BLUE.isMember(ballCarrier) && !ballCarrier.isHuman()) {
//            Player.humanPlayer.setController(new AIController());
       //     ballCarrier.setController(HumanController.get());
        }
    }

    public Player getBallCarrier() {
        return ballCarrier;
    }

    @Override
    public void action(double timeDiff) {
        Ball.get().getPosition().set(ballCarrier.getPosition());
    }

    @Override
    public int getID() {
        return id;
    }
}
