/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hot.ball.help.math.Position.DoublePosition;
import com.hot.ball.hotball.controller.Controller;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.AIController;
import com.hot.ball.hotball.controller.ai.roles.Role;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.BallState;
import com.hot.ball.hotball.universe.ball.Controlled;
import com.hot.ball.hotball.universe.ball.InAir;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;

/**
 *
 * @author Dromlius
 */
public class Player extends GameObject {
//---------DEFAULT PLAYERS----------------------

    public static Player Felix = new Player("Felix", new Stats(1, 0, 0), null);
    public static Player Friedrich = new Player("Friedrich", new Stats(0, 1, 0), null);
    public static Player Adrian = new Player("Adrian", new Stats(0, 0, 1), null);
    public static Player Leo = new Player("Leo", new Stats(2, 1, 1), null);
    public static Player Patryk = new Player("Patryk", new Stats(1, 2, 1), null);
    public static Player Thomas = new Player("Thomas", new Stats(1, 1, 2), null);
    

    /*
    public static Player Dummy1 = new Player("Dummy1", new Stats(1, 1, 1), null);
    public static Player Dummy2 = new Player("Dummy2", new Stats(1, 1, 1), null);
    public static Player Dummy3 = new Player("Dummy3", new Stats(1, 1, 1), null);
    public static Player Dummy4 = new Player("Dummy4", new Stats(1, 1, 1), null);
    public static Player Dummy5 = new Player("Dummy5", new Stats(1, 1, 1), null);
    public static Player Dummy6 = new Player("Dummy6", new Stats(1, 1, 1), null);*/

    @Override
    protected double getDECAY_FACTOR() {
    	return 4;
    }

    private static Player humanPlayer;

    public static Player getHumanPlayer() {
        return humanPlayer;
    }

    public static void setHumanPlayer(Player human) {
        if (!human.equals(humanPlayer)) {
            human.setController(HumanController.get());
            if (humanPlayer != null) {
                humanPlayer.setController(new AIController());
            }
            humanPlayer = human;
        }
    }

    private final String name;
    private Team team;

    private Controller controller;

    private double facing;

    private final TackleZone tackleZone;
    private final Role role;

    private final double totalMaxSpeed;
    private final int throwPower;

    private double currentMaxSpeed;
    
    private double minChanceToHit;
    
    private Player(String name, Stats stats, Role role) {
        super(new DoublePosition(0, 0), 24);
        this.name = name;

        totalMaxSpeed = 200 * stats.getSpeed();
        throwPower = (int) (1000 * stats.getPower());
        tackleZone = new TackleZone(this, (int) (85* stats.getTackle()));
        
        minChanceToHit = stats.getChance() / 100;
        
        currentMaxSpeed = totalMaxSpeed;

        this.role = role;
        controller = new AIController();

        this.facing = Math.random() * 2 * Math.PI;
    }

    public String getName() {
        return name;
    }

    public int getThrowPower() {
        return throwPower;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public double getCurrentMaxSpeed() {
        return currentMaxSpeed;
    }

    public double getTackleZoneSize() {
        return tackleZone.getCurrentFactor()*tackleZone.maxSize;
    }
    
    private void setController(Controller controller) {
        this.controller = controller;
    }

    public boolean isHuman() {
        return controller instanceof HumanController;
    }

    private static final float ANIMATION_TIMER = 1f / 14;
    private static final Animation[] WALKING_ANIMATION;
    private static final Animation[] DRIBBLING_ANIMATION;
    private static final TextureRegion[] STANDING_TEXTURE;
    private static final TextureRegion[] HOLDING_TEXTURE;
    private static final TextureRegion[] THROWING_TEXTURE;

    private float totalAnimationTime = (float) (Math.random() * 10);

    static {
        TextureRegion[][][] split = new TextureRegion[][][]{TextureRegion.split(new Texture(Gdx.files.internal("res/player0.png")), 64, 64), TextureRegion.split(new Texture(Gdx.files.internal("res/player1.png")), 64, 64), TextureRegion.split(new Texture(Gdx.files.internal("res/player2.png")), 64, 64)};

        WALKING_ANIMATION = new Animation[3];
        DRIBBLING_ANIMATION = new Animation[3];
        STANDING_TEXTURE = new TextureRegion[3];
        HOLDING_TEXTURE = new TextureRegion[3];
        THROWING_TEXTURE = new TextureRegion[3];
        for (int i = 0; i < 3; i++) {
            WALKING_ANIMATION[i] = new Animation(ANIMATION_TIMER, split[i][1]);
            DRIBBLING_ANIMATION[i] = new Animation(ANIMATION_TIMER, split[i][0]);
            STANDING_TEXTURE[i] = split[i][1][0];
            HOLDING_TEXTURE[i] = split[i][2][0];
            THROWING_TEXTURE[i] = split[i][2][1];
        }
    }

    public double getFacing() {
        return facing;
    }

    @Override
    public void draw(Graphics g) {
        tackleZone.draw(g);
        int spriteColor = isHuman() ? 0 : ((team.getColor() == TeamColor.Blue) ? 1 : 2);
        TextureRegion keyFrame;
        BallState ballState = Ball.get().getState();
        if (ballState instanceof InAir && this.equals(((InAir) ballState).getThrower()) && ((InAir) ballState).getAirTime() < 0.2) {
            keyFrame = THROWING_TEXTURE[spriteColor];
        } else if (getCurrentVelocity().getLength() > 12) {
            if (Ball.get().isControlledBy(this)) {
                keyFrame = DRIBBLING_ANIMATION[spriteColor].getKeyFrame(totalAnimationTime, true);
            } else {
                keyFrame = WALKING_ANIMATION[spriteColor].getKeyFrame(totalAnimationTime, true);
            }
        } else if (Ball.get().isControlledBy(this)) {
            keyFrame = HOLDING_TEXTURE[spriteColor];
        } else {
            keyFrame = STANDING_TEXTURE[spriteColor];
        }
        g.drawImage(keyFrame, getPosition().getRoundX(), getPosition().getRoundY(), 32, 32, facing);
        if (isHuman() && Ball.get().isControlledBy(this) && getChanceToHit() > 0) {
            g.drawString("" + (int) (100 * getChanceToHit()), 10, 40);
        }
    }

    public final static double MAX_THROW_DIST = 300;
    private double chanceToHit;
    private final static double TAKEDOWNTIME = 1.0;
    private double currentTakeDownTime = 0;

    
    public void calcChanceToHit(int enemyTZ) {
    	double adj_max_throw_dist = MAX_THROW_DIST + minChanceToHit * 800;
    	
        double dist = getPosition().getDistance(getTeam().getAttacking().getPosition());
        chanceToHit = Math.max(minChanceToHit, Math.min(0.99, (-dist / adj_max_throw_dist + (float) Court.BASKET_DIST_FROM_OUTLINE / adj_max_throw_dist + 0.95 + minChanceToHit)) / Math.pow(2, enemyTZ));
    }

    public double getChanceToHit() {
        return chanceToHit;
    }

    @Override
    public void action(double timeDiff) {
        totalAnimationTime += timeDiff;

        int enemyTZ = 0;
        Player closestEnemy = null;
        double closestEnemyDistance = Double.POSITIVE_INFINITY;

        int friendlyTZ = 0;
        for (Zone z : getInterferingZones()) {
            if ((z instanceof TackleZone)) {
                Player tackler = ((TackleZone) z).getPlayer();
                if (!this.equals(tackler)) {
                    if (tackler.getTeam() == getTeam()) {
                        friendlyTZ++;
                    } else {
                        enemyTZ++;
                        double enemyDistance = tackler.getPosition().getDistance(getPosition());
                        if (enemyDistance < closestEnemyDistance) {
                            closestEnemy = tackler;
                            closestEnemyDistance = enemyDistance;
                        }
                    }
                }
            }
        }
        calcChanceToHit(enemyTZ);
        currentMaxSpeed = totalMaxSpeed / (1 + Math.max(0, enemyTZ - friendlyTZ));
        if (enemyTZ > 0) {
            if (Ball.get().isControlledBy(this)) {
                currentTakeDownTime = Math.min(TAKEDOWNTIME, currentTakeDownTime + timeDiff);
                if (currentTakeDownTime >= TAKEDOWNTIME) {
                    Ball.get().setState(new Controlled(closestEnemy));
                    tackleZone.reset();
                    currentTakeDownTime = 0;
                }
            }
        } else {
            currentTakeDownTime = Math.max(0, currentTakeDownTime - timeDiff);
        }

        clearInterfeeringZones();
        accelerate(timeDiff, controller.getMoveVector(this));
        facing = controller.getFacing(this);
        tackleZone.action(timeDiff);
    }

}
