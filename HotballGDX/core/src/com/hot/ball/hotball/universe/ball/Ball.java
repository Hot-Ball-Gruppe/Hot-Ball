/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.zone.Zone;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Stack;

/**
 *
 * @author Dromlius
 */
public class Ball extends GameObject {

    public static int BALL_ID = 0;

    private static Ball theBall;

    public static void create(Position.DoublePosition startingPosition) {
        theBall = new Ball(startingPosition, new InAir(null, Vector.NULL_VECTOR, 0));
    }

    public static void create(Player ballCarrier) {
        theBall = new Ball(new Position.DoublePosition(ballCarrier.getPosition()), new Controlled(ballCarrier));
    }

    public static Ball get() {
        return theBall;
    }

    private BallState state;

    private Ball(Position.DoublePosition startingPos, BallState state) {
        super(startingPos, 16);
        this.state = state;

        texture = new TextureRegion(new Texture(Gdx.files.internal("res/basketball 2.png")));
        /* try {
         textureImg = ImageIO.read(new File("res/ball.png"));
         } catch (IOException ioe) {
         ioe.printStackTrace();
         }*/
    }

    @Override
    public void action(double timeDiff) {
        state.action(timeDiff);
        clearInterfeeringZones();
    }

    public void setState(BallState state) {
        this.state = state;
    }

    public BallState getState() {
        return state;
    }

    public boolean isControlledBy(Player p) {
        return (getState() instanceof Controlled) && p.equals(((Controlled) getState()).getBallCarrier());
    }

    private BufferedImage textureImg;

    @Override
    public void draw(Graphics2D g) {
        if (getState() instanceof InAir) {
            g.drawImage(textureImg, (int) (getPosition().getRoundX() - getSize()), (int) (getPosition().getRoundY() - getSize()), null);
        }
        //  g.setColor(Color.ORANGE);
        // g.fillOval((int) (getPosition().getRoundX() - getSize()), (int) (getPosition().getRoundY() - getSize()), (int) (2 * getSize()), (int) (2 * getSize()));
    }

    private final TextureRegion texture;

    @Override
    public void draw(Graphics g) {
        if (getState() instanceof InAir) {
            g.drawImage(texture, getPosition().getRoundX(), getPosition().getRoundY(), getCurrentVelocity().getTheta());
        }
    }

    public void throwBall(Position.DoublePosition mousePosition) {
        Player carrier = ((Controlled) getState()).getBallCarrier();
        setState(new InAir(carrier, new Vector(1000, getPosition().angleBetween(mousePosition), null), carrier.getChanceToHit()));
    }

    @Override
    protected double getDECAY_FACTOR() {
        return 0.62;
    }

    @Override
    public double getMaxSpeed() {
        return 3000;
    }

    @Override
    public Stack<Zone> getInterferingZones() {
        return super.getInterferingZones(); //To change body of generated methods, choose Tools | Templates.
    }

}
