/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.court;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.ui.Graphics;

/**
 *
 * @author Dromlius
 */
public class Court {

    public final static int OFFSET_X = 200;
    public final static int BASKET_DIST_FROM_OUTLINE = 120;
    public final static int COURT_WIDTH;
    public final static int COURT_HEIGHT;

    private final static Texture COURT_LINES_TEXTURE;

    static {
        COURT_LINES_TEXTURE = new Texture(Gdx.files.internal("res/court.png"));
        COURT_WIDTH = COURT_LINES_TEXTURE.getWidth();
        COURT_HEIGHT = COURT_LINES_TEXTURE.getHeight();
    }

    public static enum Type {
        Wood, Fire, Ice;
    }

    private static Court singleton;

    public static void generate(Type type) {
        if (singleton != null) {
            throw new RuntimeException("Court already created");
        }
        singleton = new Court(type);
    }

    public static Court get() {
        if (singleton == null) {
            throw new RuntimeException("Court not yet created");
        }
        return singleton;
    }

    private final Type type;

    private final Music themeMusic;
    private final Texture courtTexture;

    private final Basket leftBasket, rightBasket;

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    private Court(Type type) {
        this.type = type;
        themeMusic = Gdx.audio.newMusic(Gdx.files.internal("aud/" + type.name() + ".wav"));
        themeMusic.setLooping(true);
        courtTexture = new Texture(Gdx.files.internal("res/" + type.name() + ".png"));
        leftBasket = new Basket(new Position.DoublePosition(-OFFSET_X / 2, COURT_HEIGHT / 2));
        rightBasket = new Basket(new Position.DoublePosition(COURT_WIDTH + OFFSET_X / 2, COURT_HEIGHT / 2));
    }

    public Music getThemeMusic() {
        return themeMusic;
    }

    public Basket getLeftBasket() {
        return leftBasket;
    }

    public Basket getRightBasket() {
        return rightBasket;
    }
    
    public void draw(Graphics g) {
        for (int x = 0; x <= Gdx.graphics.getWidth() / courtTexture.getWidth(); x++) {
            for (int y = 0; y <= Gdx.graphics.getHeight() / courtTexture.getHeight(); y++) {
                g.drawImageNotCetral(courtTexture, x * courtTexture.getWidth() - OFFSET_X, y * courtTexture.getHeight());
            }
        }
        g.drawImageNotCetral(COURT_LINES_TEXTURE, 0, 0);
    }
}
