/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.controller.Controller;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.BallState;
import com.hot.ball.hotball.universe.ball.Controlled;
import com.hot.ball.hotball.universe.ball.InAir;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;
import java.awt.Graphics2D;

/**
 *
 * @author Dromlius
 */
public class Player extends GameObject {

    public static Player humanPlayer;

    //  private final String name;
    private final Team team;

    private Controller controller;

    private double facing;

    private final TackleZone tackleZone;

    public Player(String name, Team team, Controller controller, Position.DoublePosition startingPos) {
        super(startingPos, 24);
        // this.name = name;
        this.team = team;
        // this.facing = facing;
        setController(controller);
        tackleZone = new TackleZone(this);
    }

    public Team getTeam() {
        return team;
    }

    public final void setController(Controller controller) {
        this.controller = controller;
        if (controller instanceof HumanController) {
            humanPlayer = this;
        }
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
    public void draw(Graphics2D g) {
        tackleZone.draw(g);
        int spriteColor = isHuman() ? 2 : ((team.getColor() == TeamColor.Blue) ? 0 : 1);
        //  BufferedImage texture = TEXTURES[spriteColor][Ball.get().isControlledBy(this) ? 1 : 0];
        //    AffineTransform tx = AffineTransform.getRotateInstance(facing + Math.PI / 2, texture.getWidth() / 2, texture.getHeight() / 2);
        //    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        //  g.drawImage(op.filter(texture, null), (int) (getPosition().getX() - getSize()), (int) (getPosition().getY() - getSize()), null);
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
                keyFrame = DRIBBLING_ANIMATION[spriteColor].getKeyFrame(totalTime, true);
            } else {
                keyFrame = WALKING_ANIMATION[spriteColor].getKeyFrame(totalTime, true);
            }
        } else if (Ball.get().isControlledBy(this)) {
            keyFrame = HOLDING_TEXTURE[spriteColor];
        } else {
            keyFrame = STANDING_TEXTURE[spriteColor];
        }
        g.drawImage(keyFrame, getPosition().getRoundX(), getPosition().getRoundY(), 32, 32, facing);
    }

    private final static double TAKEDOWNTIME = 2.5;
    private double currentTakeDownTime = 0;

    private float totalTime = (float) (Math.random() * 10);

    @Override
    public void action(double timeDiff) {
        totalTime += timeDiff;

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

                //   System.out.println("I "+getId()+ "("+getTeam()+") am in TZ of: "+((TackleZone)z).getPlayer().getId());
            }
        }
        maxSpeed = TOTALMAXSPEED / (1 + Math.max(0, enemyTZ - friendlyTZ));
        if (enemyTZ > 0) {
            if (Ball.get().isControlledBy(this)) {
                currentTakeDownTime = Math.min(TAKEDOWNTIME, currentTakeDownTime + timeDiff);
                //System.out.println("TakoOver: "+currentTakeDownTime);
                if (currentTakeDownTime >= TAKEDOWNTIME) {
                    Ball.get().setState(new Controlled(closestEnemy));
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

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            if (Ball.get().isControlledBy(this)) {
                Ball.get().throwBall(new Position.DoublePosition(500 * Math.random(), 500 * Math.random()));
            }
        }
    }

    @Override
    protected double getDECAY_FACTOR() {
        return 4;
    }

    private static final double TOTALMAXSPEED = 200;

    private double maxSpeed = TOTALMAXSPEED;

    @Override
    public double getMaxSpeed() {
        return maxSpeed;
    }

}
