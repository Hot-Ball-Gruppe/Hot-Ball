/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.hot.ball.hotball.universe.court.Court;
import java.awt.Color;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Dromlius
 */
public class Graphics {

    private static Graphics singleton;

    public static void create() {
        if (singleton == null) {
            singleton = new Graphics();
            return;
        }
        throw new RuntimeException("Graphics already created!");
    }

    public static Graphics get() {
        if (singleton == null) {
            throw new RuntimeException("Graphics not yet created!");
        }
        return singleton;
    }

    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    private final BitmapFont font;
    
    private final Queue<Overlay> overlays;

    private Graphics() {
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
        this.font = new BitmapFont(Gdx.files.internal("other/font.fnt"));
        overlays = new LinkedList<>();
    }

    public void ready() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        spriteBatch.begin();
    }

    public void draw(double deltaTime) {
        if (!overlays.isEmpty() && overlays.peek().draw(this, deltaTime)) {
            overlays.remove();
        }
        shapeRenderer.end();
        spriteBatch.end();
    }

    public void drawString(String text, int x, int y){
        font.draw(spriteBatch, text, x, y);
    }
    
    public void drawBackgroungImage(Texture img) {
        spriteBatch.draw(img, 0, 0);
    }

    public void drawImageScreenCenter(Texture img) {
        spriteBatch.draw(img, (Gdx.graphics.getWidth() - img.getWidth()) / 2, (Gdx.graphics.getHeight() - img.getHeight()) / 2);
    }

    public void drawImageScreenCenter(TextureRegion img) {
        spriteBatch.draw(img, (Gdx.graphics.getWidth() - img.getRegionWidth()) / 2, (Gdx.graphics.getHeight() - img.getRegionHeight()) / 2);
    }

    public void drawImageNotCetral(Texture img, int x, int y) {
        spriteBatch.draw(img, x + Court.OFFSET_X, y);
    }

    public void drawImage(Texture img, int x, int y) {
        spriteBatch.draw(img, x - img.getWidth() / 2 + Court.OFFSET_X, y - img.getHeight() / 2);
    }

    public void drawImage(TextureRegion img, int x, int y) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2 + Court.OFFSET_X, y - img.getRegionHeight() / 2);
    }

    public void drawImage(TextureRegion img, int x, int y, double theta) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2 + Court.OFFSET_X, y - img.getRegionHeight() / 2, img.getRegionWidth() / 2, img.getRegionHeight() / 2, img.getRegionWidth(), img.getRegionHeight(), 1, 1, (float) Math.toDegrees(theta - Math.PI / 2));
    }

    public void drawImage(TextureRegion img, int x, int y, int origin_x, int origin_y, double theta) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2 + Court.OFFSET_X, y - img.getRegionHeight() / 2, origin_x, origin_y, img.getRegionWidth(), img.getRegionHeight(), 1, 1, (float) Math.toDegrees(theta - Math.PI / 2));
    }

    public void drawImage(TextureRegion img, int x, int y, double factor, Object nullObj) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2 + Court.OFFSET_X, y - img.getRegionHeight() / 2, img.getRegionWidth() / 2, img.getRegionHeight() / 2, img.getRegionWidth(), img.getRegionHeight(), (float) factor, (float) factor, 0);
    }

    @Deprecated
    public void setColor(Color color) {
        com.badlogic.gdx.graphics.Color gdxColor = new com.badlogic.gdx.graphics.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        // spriteBatch.setColor(gdxColor);
        shapeRenderer.setColor(gdxColor);
    }

    public void addOverlay(Overlay o) {
        overlays.add(o);
    }

}
