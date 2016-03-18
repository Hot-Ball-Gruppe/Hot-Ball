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
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Dromlius
 */
public class Court {

    private final static Texture COURT_LINES_TEXTURE;
    private final static int OFFSET_X = 200;
    private final static int BASKET_DIST_FROM_OUTLINE = 120;

    static {
        COURT_LINES_TEXTURE = new Texture(Gdx.files.internal("res/court.png"));
    }

    public static int getOFFSET_X() {
        return OFFSET_X;
    }

    public static int getCOURT_WIDTH() {
        return COURT_LINES_TEXTURE.getWidth();
    }

    public static int getCOURT_HEIGHT() {
        return COURT_LINES_TEXTURE.getHeight();
    }

    public static int getBASKET_DIST_FROM_OUTLINE() {
        return BASKET_DIST_FROM_OUTLINE;
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

    private Court(Type type) {
        this.type = type;
        themeMusic = Gdx.audio.newMusic(Gdx.files.internal("aud/" + type.name() + ".wav"));
        themeMusic.setLooping(true);
        courtTexture = new Texture(Gdx.files.internal("res/" + type.name() + ".png"));
        new Basket(new Position.DoublePosition(-OFFSET_X / 2, getCOURT_HEIGHT() / 2), Team.RED);
        new Basket(new Position.DoublePosition(getCOURT_WIDTH()+OFFSET_X / 2, getCOURT_HEIGHT() / 2), Team.BLUE);

    }

    public Music getThemeMusic() {
        return themeMusic;
    }

    public void draw(Graphics g) {
        for (int x = 0; x <= Gdx.graphics.getWidth() / courtTexture.getWidth(); x++) {
            for (int y = 0; y <= Gdx.graphics.getHeight() / courtTexture.getHeight(); y++) {
                g.drawImageNotCetral(courtTexture, x * courtTexture.getWidth() - getOFFSET_X(), y * courtTexture.getHeight());
            }
        }
        g.drawImageNotCetral(COURT_LINES_TEXTURE, 0, 0);
    }
}
