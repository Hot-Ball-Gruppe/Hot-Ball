/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.logic;

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
    
    public int blueScore,redScore;
    
    public void start() {
        GameLoop.create();
        GameLoop.get().start();
    }

    public void reset() {
        Team.RED.reset();
        Team.BLUE.reset();
    }

}
