package com.hot.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class NewGameScreen extends AbstractGameScreen {

    SpriteBatch batch;
    Texture Hintergrund;
    Texture Headline;
    Texture[] Schaltflaechen = {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    int[] x = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] y = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    public NewGameScreen(Game game) {
        super(game);
        create();
    }

    public void create() {
        batch = new SpriteBatch();

        Hintergrund = new Texture(Gdx.files.internal("res/NewGBcgnd.png"));
        Headline = new Texture(Gdx.files.internal("res/NewGHeadln.png"));
        Schaltflaechen[0] = new Texture(Gdx.files.internal("res/NewGItem1.png"));
        Schaltflaechen[1] = new Texture(Gdx.files.internal("res/NewGItem2.png"));
        Schaltflaechen[2] = new Texture(Gdx.files.internal("res/NewGItem3.png"));
        Schaltflaechen[3] = new Texture(Gdx.files.internal("res/NewGItem4.png"));
        Schaltflaechen[4] = new Texture(Gdx.files.internal("res/NewGItem5.png"));
        Schaltflaechen[5] = new Texture(Gdx.files.internal("res/NewGItem6.png"));
        Schaltflaechen[6] = new Texture(Gdx.files.internal("res/NewGItem7.png"));

    }

    @Override
    public void render(float deltaTime) {

        y[0] = 800 - Headline.getHeight() - 50 - Schaltflaechen[0].getHeight() / 2 - 20;//Lage der obersten Fenser
        y[1] = y[0] - Schaltflaechen[0].getHeight() / 2 - Schaltflaechen[1].getHeight() / 2 - 20;
        y[2] = y[1] - Schaltflaechen[1].getHeight() / 2 - Schaltflaechen[2].getHeight() / 2 - 20 - 5 - 50;

        Gdx.gl.glClearColor(0.5f, 0.5f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//		if (Gdx.input.isTouched())
        if (Gdx.input.isKeyJustPressed(Keys.ALT_RIGHT)) {
            game.setScreen(new GameScreen(game));
        }

        batch.begin();
        batch.draw(Hintergrund, 0, 0);
        batch.draw(Headline, (1280 - Headline.getWidth()) / 2, 800 - Headline.getHeight() - 50);

        batch.draw(Schaltflaechen[0], (1280 - Schaltflaechen[0].getWidth()) / 2, y[0] - Schaltflaechen[0].getHeight() / 2);
        batch.draw(Schaltflaechen[1], (1280 - Schaltflaechen[1].getWidth()) / 2, y[1] - Schaltflaechen[1].getHeight() / 2);

        batch.draw(Schaltflaechen[2], (1280 - Schaltflaechen[2].getWidth()) / 2, y[2] - Schaltflaechen[2].getHeight() / 2);
        batch.draw(Schaltflaechen[3], (1280 - Schaltflaechen[2].getWidth()) / 2 - Schaltflaechen[2].getWidth() - 30, y[2] - Schaltflaechen[2].getHeight() / 2);
        batch.draw(Schaltflaechen[4], (1280 - Schaltflaechen[2].getWidth()) / 2 + Schaltflaechen[2].getWidth() + 30, y[2] - Schaltflaechen[2].getHeight() / 2);

        batch.draw(Schaltflaechen[5], 0, 0);
        batch.draw(Schaltflaechen[6], 1280 - Schaltflaechen[6].getWidth(), 0);

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
