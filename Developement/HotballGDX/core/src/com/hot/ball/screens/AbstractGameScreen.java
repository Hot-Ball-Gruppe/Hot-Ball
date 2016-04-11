package com.hot.ball.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.hot.ball.game.Assets;

public abstract class AbstractGameScreen implements Screen {

    protected Game game;

    public AbstractGameScreen(Game game) {
        this.game = game;
    }

    @Override
    public abstract void render(float deltaTime);

    @Override
    public abstract void resize(int width, int height);

    @Override
    public abstract void show();

    @Override
    public abstract void hide();

    @Override
    public abstract void pause();

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
        Assets.instance.dispose();
    }
}
