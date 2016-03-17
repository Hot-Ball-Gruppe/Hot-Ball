/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.zone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.TeamColor;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;

/**
 *
 * @author Inga
 */
public class TackleZone implements Zone {

    private final static TextureRegion[] texture = new TextureRegion[4];
    private final static double DRAW_SCALE;

    private final static double maxX = 85;
    private final static double maxY = maxX;
    private final static double minFactor = 0.75;
    private final static double ballCarrierFactor = 1;
    private final static double changePerSecond = 1.5;

    static {
        for (int i = 0; i < 4; i++) {
            texture[i] = new TextureRegion(new Texture(Gdx.files.internal("res/Tackle" + i + ".png")));
        }
        DRAW_SCALE = 2 * maxX / texture[0].getRegionWidth();
    }

    private final Player player;
    private double currentFactor = 1;

    @SuppressWarnings("LeakingThisInConstructor")
    public TackleZone(Player player) {
        this.player = player;
        ALL_ZONES.add(this);
    }

    public void action(double timeDiff) {
        if (currentFactor >= minFactor) {
            currentFactor += (1 - player.getCurrentVelocity().getLength() / player.getMaxSpeed() - currentFactor) * changePerSecond * timeDiff;
            currentFactor = Math.min(1, Math.max(minFactor, currentFactor));
        } else {
            currentFactor += timeDiff * changePerSecond / 4;
        }
    }

    public void draw(Graphics2D g) {
        g.setColor(player.getTeam().getColor().getTransColor());
        // Ellipse2D e = new Ellipse2D.Double(maxX, maxY, maxX, maxX);
        // g.draw(new Ellipse2D.Double(player.getPosition().getX()-maxX*currentFactor, player.getPosition().getY()-maxY*currentFactor, 2*maxX*currentFactor, 2*maxY*currentFactor));
        g.fill(AffineTransform.getRotateInstance(player.getFacing() + Math.PI / 2, player.getPosition().getX(), player.getPosition().getY()).createTransformedShape(new Ellipse2D.Double(player.getPosition().getX() - maxX * currentFactor, player.getPosition().getY() - maxY * currentFactor, 2 * maxX * currentFactor, 2 * maxY * currentFactor)));

        //   g.fillOval((int) (player.getPosition().getX()-maxX*currentFactor), (int) (player.getPosition().getY()-maxY*currentFactor), (int) (2*maxX*currentFactor), (int) (2*maxY*currentFactor));
    }

    public void draw(Graphics g) {
        int spriteColor = player.isHuman() ? 0 : ((player.getTeam().getColor() == TeamColor.Blue) ? 1 : ((Ball.get().isControlledBy(player) ? 3 : 2)));
        g.drawImage(texture[spriteColor], player.getPosition().getRoundX(), player.getPosition().getRoundY(), DRAW_SCALE * currentFactor * getBallCarrierFactor(), null);
    }

    @Override
    public boolean contains(GameObject go) {
        double cos = Math.cos(player.getFacing());
        double sin = Math.sin(player.getFacing());
        double dx = go.getPosition().getX() - player.getPosition().getX();
        double dy = go.getPosition().getY() - player.getPosition().getY();
        return Math.pow((cos * dx + sin * dy) / (maxX * currentFactor * getBallCarrierFactor() + go.getSize()), 2)
                + Math.pow((sin * dx - cos * dy) / (maxY * currentFactor * getBallCarrierFactor() + go.getSize()), 2) <= 1;
    }

    public Player getPlayer() {
        return player;
    }

    public double getCurrentFactor() {
        return currentFactor;
    }

    public void reset() {
        currentFactor = 0;
    }

    public double getBallCarrierFactor() {
        return Ball.get().isControlledBy(player)?ballCarrierFactor:1;
    }
    
    

}
