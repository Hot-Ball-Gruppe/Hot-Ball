/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.court;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.player.Team;
import java.awt.Graphics2D;

/**
 *
 * @author Dromlius
 */
public class Basket extends GameObject {

    private final static Texture TEXTURE;
    private final static Texture TEXTURE_FLIPPED;

    static {
        TEXTURE = new Texture(Gdx.files.internal("res/Basket.png"));
        TEXTURE_FLIPPED = new Texture(Gdx.files.internal("res/Basket_F.png"));
    }

    private final Team defenders;
    private final Team attackers;

    private int lastAttack = -1;

    public Basket(Position.DoublePosition position, Team defenders) {
        super(position, 0);
        this.defenders = defenders;
        this.attackers = defenders.getOpponent();
        defenders.setAttacking(this);
    }

    @Override
    public void action(double timeDiff) {
    }

    @Override
    public void draw(Graphics2D g) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void draw(Graphics g) {
        if (getPosition().getX() > 0) {
            g.drawImage(TEXTURE_FLIPPED, getPosition().getRoundX(), getPosition().getRoundY());
        } else {
            g.drawImage(TEXTURE, getPosition().getRoundX(), getPosition().getRoundY());
        }
    }

    @Override
    protected double getDECAY_FACTOR() {
        return 0;
    }

    @Override
    public double getMaxSpeed() {
        return 0;
    }

    public int getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(int lastAttack) {
        this.lastAttack = lastAttack;
    }

}
