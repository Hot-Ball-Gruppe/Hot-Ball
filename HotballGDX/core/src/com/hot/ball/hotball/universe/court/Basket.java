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

/**
 *
 * @author Dromlius
 */
public class Basket extends GameObject {

    private int lastAttack = -1;
    
    private Team attacking;
    
    public Basket(Position.DoublePosition position) {
        super(position, 0);
    }

    public int getLastAttack() {
        return lastAttack;
    }

    public void setLastAttack(int lastAttack) {
        this.lastAttack = lastAttack;
    }

    private final static Texture TEXTURE;
    private final static Texture TEXTURE_FLIPPED;

    static {
        TEXTURE = new Texture(Gdx.files.internal("res/Basket.png"));
        TEXTURE_FLIPPED = new Texture(Gdx.files.internal("res/Basket_F.png"));
    }

    @Override
    public void draw(Graphics g) {
        if (getPosition().getX() > 0) {
            g.drawImageRel(TEXTURE_FLIPPED, getPosition().getRoundX(), getPosition().getRoundY());
        } else {
            g.drawImageRel(TEXTURE, getPosition().getRoundX(), getPosition().getRoundY());
        }
    }

    @Override
    protected double getDECAY_FACTOR() {
        return 0;
    }

    @Override
    public double getCurrentMaxSpeed() {
        return 0;
    }

    @Override
    public void action(double timeDiff) {
    }

    public Team getAttacking() {
        return attacking;
    }

    public void setAttacking(Team attacking) {
        this.attacking = attacking;
    }

}
