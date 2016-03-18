/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.logic;

import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.Controlled;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Inga
 */
public class LogicCore {

    private static LogicCore singleton;

    public static void create() {
        if (singleton == null) {
            singleton = new LogicCore();
            return;
        }
        throw new RuntimeException("LogicCore already created!");
    }

    public static LogicCore get() {
        if (singleton == null) {
            throw new RuntimeException("LogicCore already created!");
        }
        return singleton;
    }

    private LogicCore() {
    }

    public void start() {
        GameLoop.create();
        GameLoop.get().start();
    }

    public void reset() {
        for (int i = 0; i < Team.BLUE.getMembers().size(); i++) {
            Team.BLUE.getMembers().get(i).getPosition().setX(70);
            Team.BLUE.getMembers().get(i).getPosition().setY(i * 100 + 100);
        }

        for (int i = 0; i < Team.RED.getMembers().size(); i++) {
            Team.RED.getMembers().get(i).getPosition().setX(1000 - 70);
            Team.RED.getMembers().get(i).getPosition().setY(i * 100 + 100);
        }
     //   Player.humanPlayer.calcChanceToHit(0);
       // Ball.get().setState(new Controlled(Player.humanPlayer));
    }

}
