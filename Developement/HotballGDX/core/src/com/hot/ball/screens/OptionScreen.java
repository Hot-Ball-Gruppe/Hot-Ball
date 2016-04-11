package com.hot.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class OptionScreen extends AbstractGameScreen {

    SpriteBatch batch;
    Texture Hintergrund;
    Texture Headline;
    Texture Back;
    Texture[] Schaltflaechen = {null, null, null, null, null, null, null};
    int[] x = {0, 0, 0, 0, 0, 0, 0};
    int[] y = {0, 0, 0, 0, 0, 0, 0};

    public OptionScreen(Game game) {
        super(game);
        create();
    }

    public void create() {
        batch = new SpriteBatch();

        Hintergrund = new Texture(Gdx.files.internal("res/menu/OptBcgnd.png"));
        Headline = new Texture(Gdx.files.internal("res/menu/OptionHeadln.png"));
        Schaltflaechen[0] = new Texture(Gdx.files.internal("res/menu/OptItem1.png"));
        Schaltflaechen[1] = new Texture(Gdx.files.internal("res/menu/OptItem2.png"));
        Schaltflaechen[2] = new Texture(Gdx.files.internal("res/menu/OptItem3.png"));
        Schaltflaechen[3] = new Texture(Gdx.files.internal("res/menu/OptItem4.png"));
        Schaltflaechen[4] = new Texture(Gdx.files.internal("res/menu/OptItem5.png"));
        Schaltflaechen[5] = new Texture(Gdx.files.internal("res/menu/OptItem6.png"));
        Back = new Texture(Gdx.files.internal("res/menu/OptBack.png"));

    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.justTouched()) {
            game.setScreen(new MenuScreen(game));
        }

        batch.begin();
        batch.draw(Hintergrund, 0, 0);
        batch.draw(Headline, (1280 - Headline.getWidth()) / 2, 620);
        batch.draw(Back, (1280 - Back.getWidth()) / 2, 40);
        for (int i = 0; i <= 6 && Schaltflaechen[i] != null; i++) {
            batch.draw(Schaltflaechen[i], x[i] + 200 + (1480 / 2) * (i % 2), y[i] + 400 - (i / 2) * 150);
        }

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub

    }

    @Override
    public void show() {
        // TODO Auto-generated method stub

    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub

    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub

    }

}
