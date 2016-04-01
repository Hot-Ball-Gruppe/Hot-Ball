/**
 *
 */
package com.hot.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen extends AbstractGameScreen {

    SpriteBatch batch;
    Texture Hintergrund;
    Texture Headline;
    Texture[] Schaltflaechen = {null, null, null, null, null, null, null};
    int[] x = {0, 0, 0, 0, 0, 0, 0};
    int[] y = {0, 0, 0, 0, 0, 0, 0};

    public MenuScreen(Game game) {
        super(game);
        create();
    }
private  Music menuMusic;
    public final void create() {
        batch = new SpriteBatch();

        Hintergrund = new Texture(Gdx.files.internal("res/menu/MenuBcgnd.png"));
        Headline = new Texture(Gdx.files.internal("res/menu/MenuHeadln.png"));
        Schaltflaechen[0] = new Texture(Gdx.files.internal("res/menu/MenuItem1.png"));
        Schaltflaechen[1] = new Texture(Gdx.files.internal("res/menu/MenuItem2.png"));
        Schaltflaechen[2] = new Texture(Gdx.files.internal("res/menu/MenuItem3.png"));
        Schaltflaechen[3] = new Texture(Gdx.files.internal("res/menu/MenuItem4.png"));
        Schaltflaechen[4] = new Texture(Gdx.files.internal("res/menu/MenuItem5.png"));
        Schaltflaechen[5] = new Texture(Gdx.files.internal("res/menu/MenuItem6.png"));
        
        menuMusic=Gdx.audio.newMusic(Gdx.files.internal("aud/Hauptmenue_loop.wav"));
        menuMusic.setLooping(true);
        menuMusic.play();
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Gdx.input.isTouched()) {
           if (Gdx.input.getY() > 3*Gdx.graphics.getHeight() / 4) {
              //  System.exit(1);
            } else if (Gdx.input.getY() > 2 * Gdx.graphics.getHeight() / 4) {
                menuMusic.stop();
                menuMusic.dispose();
                game.setScreen(new OptionScreen(game));
            } else if (Gdx.input.getY() > 1 * Gdx.graphics.getHeight() / 4) {
                menuMusic.stop();
                menuMusic.dispose();
                game.setScreen(new GameScreen(game));
                
            }

        }

        batch.begin();
        batch.draw(Hintergrund, 0, 0);
        batch.draw(Headline, (1280 - Headline.getWidth()) / 2, 620);
        for (int i = 0; i <= 6 && Schaltflaechen[i] != null; i++) {
            batch.draw(Schaltflaechen[i], x[i] + 380, y[i] + 500 - i * 120);
        }

        batch.end();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

}
