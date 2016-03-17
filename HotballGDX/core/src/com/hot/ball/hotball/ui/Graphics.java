/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.awt.Color;

/**
 *
 * @author Dromlius
 */
public class Graphics {

    public final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;

    public Graphics() {
        this.shapeRenderer = new ShapeRenderer();
        this.spriteBatch = new SpriteBatch();
    }

    public void ready() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        spriteBatch.begin();
    }

    public void draw() {
        shapeRenderer.end();
        spriteBatch.end();
    }

    public void drawBackgroungImage(Texture img) {
        spriteBatch.draw(img, 0, 0);
    }

    public void drawImage(Texture img, int x, int y) {
        spriteBatch.draw(img, x - img.getWidth() / 2, y - img.getHeight() / 2);
    }

    public void drawImage(TextureRegion img, int x, int y) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2, y - img.getRegionHeight() / 2);
    }

    public void drawImage(TextureRegion img, int x, int y, double theta) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2, y - img.getRegionHeight() / 2, img.getRegionWidth() / 2, img.getRegionHeight() / 2, img.getRegionWidth(), img.getRegionHeight(), 1, 1, (float) Math.toDegrees(theta - Math.PI / 2));
    }

    public void drawImage(TextureRegion img, int x, int y, int origin_x, int origin_y, double theta) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2, y - img.getRegionHeight() / 2, origin_x, origin_y, img.getRegionWidth(), img.getRegionHeight(), 1, 1, (float) Math.toDegrees(theta - Math.PI / 2));
    }

    public void drawImage(TextureRegion img, int x, int y, double factor, Object nullObj) {
        spriteBatch.draw(img, x - img.getRegionWidth() / 2, y - img.getRegionHeight() / 2, img.getRegionWidth() / 2, img.getRegionHeight() / 2, img.getRegionWidth(), img.getRegionHeight(), (float) factor, (float) factor, 0);
    }

    @Deprecated
    public void setColor(Color color) {
        com.badlogic.gdx.graphics.Color gdxColor = new com.badlogic.gdx.graphics.Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        // spriteBatch.setColor(gdxColor);
        shapeRenderer.setColor(gdxColor);
    }

}
