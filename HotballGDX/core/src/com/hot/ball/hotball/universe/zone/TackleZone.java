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

/**
 *
 * @author Inga
 */
public class TackleZone implements Zone {

    public final static double MIN_SIZE_FACTOR = 0.75;
    public final static double SIZE_CHANGE_PER_SECOND = 1.5;

    private final Player player;
    private double currentFactor = 1;

    public final double maxSize;

    @SuppressWarnings("LeakingThisInConstructor")
    public TackleZone(Player player, int maxSize) {
        //System.out.println("PLayer: "+player.getName()+" tz: "+maxSize);
        this.player = player;
        this.maxSize = maxSize;

        drawScale = 2d * maxSize / TEXTURE[0].getRegionWidth();

        ALL_ZONES.add(this);
    }

    public void action(double timeDiff) {
        if (currentFactor >= MIN_SIZE_FACTOR) {
            currentFactor += (1 - player.getCurrentVelocity().getLength() / player.getCurrentMaxSpeed() - currentFactor) * SIZE_CHANGE_PER_SECOND * timeDiff;
            currentFactor = Math.min(1, Math.max(MIN_SIZE_FACTOR, currentFactor));
        } else {
            currentFactor += timeDiff * SIZE_CHANGE_PER_SECOND / 4;
        }
    }

    @Override
    public boolean contains(GameObject go) {
        return player.getPosition().getDistance(go.getPosition()) < maxSize * currentFactor;
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

    private final static TextureRegion[] TEXTURE = new TextureRegion[4];
    private final double drawScale;

    static {
        for (int i = 0; i < 4; i++) {
            TEXTURE[i] = new TextureRegion(new Texture(Gdx.files.internal("res/Tackle" + i + ".png")));
        }
    }

    public void draw(Graphics g) {
        int spriteColor = player.isHuman() ? 0 : ((player.getTeam().getColor() == TeamColor.Blue) ? 1 : ((Ball.get().isControlledBy(player) ? 3 : 2)));
        g.drawImage(TEXTURE[spriteColor], player.getPosition().getRoundX(), player.getPosition().getRoundY(), drawScale * currentFactor, null);
    }
}
