/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.logic;

import com.hot.ball.hotball.ui.AudioManager;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.zone.Zone;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
        blockConditions = new LinkedList<>();
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
                double timeDiff = (now - lastTime) / 4000d;
                for (GameObject go : GameObject.ALL_GAMEOBJECTS) {
                    for (Zone z : Zone.ALL_ZONES) {
                        if (z.contains(go)) {
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
