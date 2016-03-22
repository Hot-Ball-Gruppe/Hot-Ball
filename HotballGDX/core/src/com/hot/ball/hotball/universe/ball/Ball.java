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

/**
 *
 * @author Dromlius
 */
public class Ball extends GameObject {

    private static Ball theBall;
    public static int BALL_ID = 0;

    public static void create(Position.DoublePosition startingPosition) {
        theBall = new Ball(startingPosition);
        theBall.setState(new InAir(null, Vector.NULL_VECTOR, 0));
    }

    public static Ball get() {
        return theBall;
    }

    private BallState state;

    private Ball(Position.DoublePosition startingPos) {
        super(startingPos, 16);

        texture = new TextureRegion(new Texture(Gdx.files.internal("res/basketball 2.png")));
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
    
    public Player getBallCarrier(){
        if(state instanceof InAir){
            System.out.println("WARNING: Ball in air! Returning thrower not carrier!!");
            return ((InAir)state).getThrower();
        }else{
            return ((Controlled)state).getBallCarrier();
        }
        
    }
    
    public boolean isControlledBy(Player p) {
        return (getState() instanceof Controlled) && p.equals(getBallCarrier());
    }

    public void throwBall(Position.DoublePosition target, int power) {
        Player carrier = getBallCarrier();
        setState(new InAir(carrier, new Vector(power, getPosition().angleBetween(target), null), carrier.getChanceToHit()));
    }

    @Override
    public double getDECAY_FACTOR() {
        return 0.62;
    }

    @Override
    public double getCurrentMaxSpeed() {
        return 3000;
    }

    private final TextureRegion texture;

    @Override
    public void draw(Graphics g) {
        if (getState() instanceof InAir) {
            g.drawImage(texture, getPosition().getRoundX(), getPosition().getRoundY(), getCurrentVelocity().getTheta());
        }
    }

}
