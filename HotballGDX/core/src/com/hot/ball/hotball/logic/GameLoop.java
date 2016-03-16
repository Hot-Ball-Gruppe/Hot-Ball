/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.logic;

import com.hot.ball.hotball.ui.UserInput;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.zone.Zone;
import java.awt.event.KeyEvent;


/**
 *
 * @author Inga
 */
public class GameLoop implements Runnable {

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
    }

    public void start() {
        if (active) {
            throw new RuntimeException("GameLoop already started");
        }
        active = true;
        lastTime = System.currentTimeMillis();
       // resume();
        loop.start();
    }

    public void terminate() {
        if (!active) {
            throw new RuntimeException("GameLoop already terminated");
        }
        running = true;
        active = false;
    }

    /* public void pause() {
        if (!running) {
            return;
            //  throw new RuntimeException("GameLoop already paused");
        }
        running = false;
    }

    public void resume() {
        if (running) {
            return;
            //throw new RuntimeException("GameLoop already resumed");
        }
        lastTime = System.currentTimeMillis();
        running = true;
    }*/
    private long lastTime = -1;

    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run() {
        while (active) {
            // System.out.println("act");
            while (Math.random()<10){//!UserInput.get().isPressed(KeyEvent.VK_SPACE)) {
                // System.out.println("run");
                long now = System.currentTimeMillis();
                double timeDiff = (now - lastTime) / 1000d;
                for (GameObject go : GameObject.ALL_GAMEOBJECTS) {
                    for(Zone z:Zone.ALL_ZONES){
                        if(z.contains(go)){
                            go.addZone(z);
                        }
                    }
                    go.action(timeDiff);
                    
                }
                lastTime = now;

                
                
                
              /*  if (Ball.get().getState() instanceof InAir) {
                    for (GameObject go : GameObject.ALL_GAMEOBJECTS) {
                        if (go instanceof Player) {
                            Player player = (Player) go;

                            if (!player.equals(((InAir) Ball.get().getState()).getThrower()) && player.getPosition().getDistance(Ball.get().getPosition()) < 40) {
                                Ball.get().setState(new Controlled(player));
                                if (player.getTeam() == Team.Blue && !player.isHuman()) {
                                    Player.humanPlayer.setController(new AIController());
                                    player.setController(HumanController.get());
                                }
                                break;
                            }
                        }
                    }
                }*/
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
    }

}
