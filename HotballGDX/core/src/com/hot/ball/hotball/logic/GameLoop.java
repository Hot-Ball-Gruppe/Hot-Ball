/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.logic;

import com.hot.ball.hotball.controller.ai.analysis.Analysis;
import com.hot.ball.hotball.ui.AudioManager;
import com.hot.ball.hotball.ui.UserInput;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.zone.Zone;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.badlogic.gdx.Game;//repmanntest
import com.hot.ball.screens.MenuScreen;//repmanntest
/**
 *
 * @author Inga
 */
public class GameLoop implements Runnable {

// repmanntest; Start
	private Game game;

	private void backToMenu() {
		// switch to menu screen
		game.setScreen(new MenuScreen(game));
	}

	public GameLoop(Game game) {
		this.game = game;
		loop = new Thread(this);
        blockConditions = new LinkedList<>();
	}
// repmanntest; Ende

	private static GameLoop singleton;

    public static void create() {
        if (singleton == null) {
            singleton = new GameLoop();
            return;
        }
        throw new RuntimeException("GameLoop already created!");
    }

    public static GameLoop get() {
        if (singleton == null) {
            throw new RuntimeException("GameLoop not yet created!");
        }
        return singleton;
    }

    private final Thread loop;

    private boolean active;
    private boolean running;

    private GameLoop() {
        loop = new Thread(this);
        blockConditions = new LinkedList<>();
        blockConditions.add(UserInput.get());
    }

    public void start() {
        if (active) {
            throw new RuntimeException("GameLoop already started");
        }
        active = true;
        resume();
        loop.start();
    }

    public void terminate() {
        if (!active) {
            throw new RuntimeException("GameLoop already terminated");
        }
        running = true;
        active = false;
        AudioManager.get().stop();
        
        backToMenu();//repmanntest
    }

    private void pause() {
        if (!running) {
            return;
            //  throw new RuntimeException("GameLoop already paused");
        }
        running = false;
        AudioManager.get().pause();
        
    }

    private void resume() {
        if (running) {
            return;
            //throw new RuntimeException("GameLoop already resumed");
        }
        lastTime = System.currentTimeMillis();
        running = true;
        AudioManager.get().resume();
    }

    private final List<BlockCondition> blockConditions;

    private long lastTime = -1;

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        while (active) {
            checkBlocked();
            while (running) {
                // System.out.println("run");
                long now = System.currentTimeMillis();
                double timeDiff = (now - lastTime) / 1000d;
                for (GameObject go : GameObject.ALL_GAMEOBJECTS) {
                    for (Zone z : Zone.ALL_ZONES) {
                        if (z.contains(go)) {
                            go.addZone(z);
                        }
                    }
                    go.action(timeDiff);

                }

                Analysis.get().recalculate();

                lastTime = now;
                checkBlocked();
                try {
                    Thread.sleep(12);
                } catch (InterruptedException ie) {
                    ie.printStackTrace();
                }
            }
            lastTime = System.currentTimeMillis();
            try {
                Thread.sleep(12);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
        System.out.println("GameEnd");
        
        backToMenu();//repmanntest
    }

    public boolean isRunning() {
        return running;
    }

    public void addBlockCondition(BlockCondition bc) {
        blockConditions.add(bc);
    }

    private void checkBlocked() {
        for (Iterator<BlockCondition> iterator = blockConditions.iterator(); iterator.hasNext();) {
            BlockCondition bc = iterator.next();
            if (bc.isBlocking()) {
                pause();
                return;
            } else if (!bc.isPermanent()) {
                iterator.remove();
            }
        }
        resume();
    }

}
