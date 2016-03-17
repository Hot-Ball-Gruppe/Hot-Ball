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
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.Controlled;
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

    // private static final BufferedImage[][] TEXTURES;
    private static final Animation ANIMATION;

    static {
        Texture spritesheet = new Texture(Gdx.files.internal("res/spritesheet 2.png"));
        TextureRegion[][] split = TextureRegion.split(spritesheet, 64, 64);
        int row = 0;
        ANIMATION = new Animation(1f / 10, new TextureRegion[]{split[0][row], split[1][row], split[2][row], split[3][row], split[4][row], split[5][row], split[6][row], split[7][row]});

        /*  TEXTURES = new BufferedImage[3][2];
         //        FileHandle internal = Gdx.files.internal("   ");
         //   Sprite s = new Sprite
         try {
         TEXTURES[0][0] = ImageIO.read(new File("res/player_B_N.png"));
         TEXTURES[0][1] = ImageIO.read(new File("res/player_B_W.png"));
         TEXTURES[1][0] = ImageIO.read(new File("res/player_R_N.png"));
         TEXTURES[1][1] = ImageIO.read(new File("res/player_R_W.png"));
         TEXTURES[2][0] = ImageIO.read(new File("res/player_Y_N.png"));
         TEXTURES[2][1] = ImageIO.read(new File("res/player_Y_W.png"));
         } catch (IOException ioe) {
         ioe.printStackTrace();
         }*/
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
    public void draw(SpriteBatch batch) {
        //   tackleZone.draw(batch);
        TextureRegion keyFrame = ANIMATION.getKeyFrame(totalTime, true);
        batch.draw(keyFrame, getPosition().getRoundX() - keyFrame.getRegionWidth() / 2, getPosition().getRoundY() - keyFrame.getRegionHeight() / 2, 33, 29, keyFrame.getRegionWidth(), keyFrame.getRegionHeight(), 1, 1, (float) Math.toDegrees(facing - Math.PI / 2));
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
