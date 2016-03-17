/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.zone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.player.Player;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Inga
 */
public class TackleZone implements Zone {

    private final static double maxX = 100;
    private final static double maxY = 100;
    private final static double minFactor = 0.25;
    private final static double changePerSecond = 1;

    private final Player player;
    private double currentFactor = 10;

    @SuppressWarnings("LeakingThisInConstructor")
    public TackleZone(Player player) {
        this.player = player;
        ALL_ZONES.add(this);
    }

    public void action(double timeDiff) {
        currentFactor += (1 - player.getCurrentVelocity().getLength() / player.getMaxSpeed() - currentFactor) * changePerSecond * timeDiff;
        currentFactor = Math.min(1, Math.max(minFactor, currentFactor));
    }

    public void draw(Graphics2D g) {
        g.setColor(player.getTeam().getColor().getTransColor());
        // Ellipse2D e = new Ellipse2D.Double(maxX, maxY, maxX, maxX);
        // g.draw(new Ellipse2D.Double(player.getPosition().getX()-maxX*currentFactor, player.getPosition().getY()-maxY*currentFactor, 2*maxX*currentFactor, 2*maxY*currentFactor));
        g.fill(AffineTransform.getRotateInstance(player.getFacing() + Math.PI / 2, player.getPosition().getX(), player.getPosition().getY()).createTransformedShape(new Ellipse2D.Double(player.getPosition().getX() - maxX * currentFactor, player.getPosition().getY() - maxY * currentFactor, 2 * maxX * currentFactor, 2 * maxY * currentFactor)));

        //   g.fillOval((int) (player.getPosition().getX()-maxX*currentFactor), (int) (player.getPosition().getY()-maxY*currentFactor), (int) (2*maxX*currentFactor), (int) (2*maxY*currentFactor));
    }

    
    private static final Texture tackleZ = new Texture(Gdx.files.internal("res/tackleTest.png"));
    public void draw(SpriteBatch batch) {
        Position.DoublePosition position = player.getPosition();
        batch.draw(tackleZ, (float)(position.getX()-maxX*currentFactor), (float)(position.getY()-maxY*currentFactor), (float)(maxX*currentFactor), (float)(maxY*currentFactor));
    }
    
    
    @Override
    public boolean contains(GameObject go) {
        double cos = Math.cos(player.getFacing());
        double sin = Math.sin(player.getFacing());
        double dx = go.getPosition().getX() - player.getPosition().getX();
        double dy = go.getPosition().getY() - player.getPosition().getY();
        return Math.pow((cos * dx + sin * dy) / (maxX * currentFactor + go.getSize()), 2)
                + Math.pow((sin * dx - cos * dy)/ (maxY * currentFactor + go.getSize()), 2)  <= 1;
    }

    public Player getPlayer() {
        return player;
    }

    public double getCurrentFactor() {
        return currentFactor;
    }


}
