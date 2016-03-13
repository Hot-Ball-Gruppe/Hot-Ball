/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotball.universe.ball;

import help.math.Position;
import help.math.Vector;
import hotball.universe.GameObject;
import hotball.universe.player.Player;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Dromlius
 */
public class Ball extends GameObject {

    private static Ball theBall;

    public static void create(Position.DoublePosition startingPosition) {
        theBall = new Ball(startingPosition, new InAir(null, Vector.NULL_VECTOR));
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
        try {
            texture = ImageIO.read(new File("res/ball.png"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void action(double timeDiff) {
        state.action(timeDiff);
    }

    public void setState(BallState state) {
        this.state = state;
    }

    public BallState getState() {
        return state;
    }

    public boolean isControlledBy(Player p){
        return (getState() instanceof Controlled) && p.equals(((Controlled)getState()).getBallCarrier());
    }
    
    private BufferedImage texture;

    @Override
    public void draw(Graphics2D g) {
        if (getState() instanceof InAir) {
            g.drawImage(texture, (int) (getPosition().getRoundX() - getSize()), (int) (getPosition().getRoundY() - getSize()), null);
        }
        //  g.setColor(Color.ORANGE);
        // g.fillOval((int) (getPosition().getRoundX() - getSize()), (int) (getPosition().getRoundY() - getSize()), (int) (2 * getSize()), (int) (2 * getSize()));
    }

    public void throwBall(Position.DoublePosition mousePosition) {
        Player carrier = ((Controlled) getState()).getBallCarrier();
        setState(new InAir(carrier, new Vector(1000, getPosition().angleBetween(mousePosition), null)));
    }

    @Override
    protected double getDECAY_FACTOR() {
        return 1;
    }

    @Override
    protected double getMaxSpeed() {
        return 500;
    }
    
    

}
