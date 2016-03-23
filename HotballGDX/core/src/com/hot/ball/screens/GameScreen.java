package com.hot.ball.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.court.Court;

public class GameScreen extends AbstractGameScreen {

    private boolean drawing;

	
	public GameScreen(Game game) {
		super(game);
		drawing=true;
	}

/*	@Override
	public void render(float deltaTime) {
		// TODO Auto-generated method stub

	}
	   // Texture tst;
 */
    @Override
    public void render(float deltaTime) {//repmann: deltaTime ist Echtzeit; kann benutzt 
    	//werden, um Geschwindigkeitsunterschiede des Systems rauszurechnen
       if (Gdx.input.isKeyJustPressed(Keys.M) || Gdx.input.isKeyJustPressed(Keys.F1) ){
			game.setScreen(new MenuScreen(game));
			drawing = false;
       }
       if (drawing) {
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            Graphics.get().ready();
            Court.get().draw(Graphics.get());
            for (GameObject toDraw : GameObject.ALL_GAMEOBJECTS) {
                toDraw.draw(Graphics.get());
            }
            //   Graphics.get().drawImageScreenCenter(tst);
            Graphics.get().draw(Gdx.graphics.getDeltaTime());
        }
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
