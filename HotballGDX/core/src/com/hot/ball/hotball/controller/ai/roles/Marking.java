/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

import com.hot.ball.help.math.Position.DoublePosition;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.controller.ai.util.Util;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.Controlled;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Inga
 */
public class Marking extends Behavior {

    @Override
    @SuppressWarnings("null")
    public Vector action(Player p) {
        Player marked = null;
        for (int indx = 0; indx < p.getTeam().getMembers().length; indx++) {
            if (p.equals(p.getTeam().getMembers()[indx])) {
                Team opponentTeam = p.getTeam().getOpponent();
                marked = opponentTeam.getMembers()[indx % opponentTeam.getMembers().length];
            }
        }
        DoublePosition oponentTarget = Ball.get().isControlledBy(marked) ? marked.getTeam().getAttacking().getPosition() : ((Controlled) Ball.get().getState()).getBallCarrier().getPosition();
        if (Util.canThrow(marked, oponentTarget)) {
            return new Vector(1, p.getPosition().angleBetween(Util.ClosestToStrecke(marked.getPosition(), oponentTarget, p.getPosition())), null);
        } else {
            return goToPlayer(p, marked);
        }
    }

}
