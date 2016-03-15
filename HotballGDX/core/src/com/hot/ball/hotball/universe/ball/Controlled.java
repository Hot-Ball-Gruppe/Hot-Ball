/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotball.universe.ball;

import hotball.controller.ai.AIController;
import hotball.controller.HumanController;
import hotball.universe.player.Player;
import hotball.universe.player.TeamColor;

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
