package com.hot.ball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

public class Hotball extends ApplicationAdapter {
	SpriteBatch batch;
	Texture doerk;
	Texture jagla;
	Texture[]	img={null,null};
	Sound[] 	sound={null,null};
	int[]		x={0,0};
	int[]		x_inc={2,1};
	int[]		y={0,0};
	int[]		y_inc={1,1};
	float	red=0,
			green=1,
			blue=0,
			alpha=1;
	Music 	music;
	
	@Override
	public void create () {
		batch = 	new SpriteBatch();
		doerk = 	new Texture("../android/assets/300px-DirkNowitzki.jpg");
		img[0] = 	doerk;
		jagla = 	new Texture("../android/assets/270px-JanJagla.jpg");
//		jagla = 	new Texture("../android/assets/Badlogic.jpg");
		
		img[1] = 	jagla;
		sound[0] = 	Gdx.audio.newSound(Gdx.files.internal("../android/assets/Windows Logoff Sound.wav"));
		sound[1] = 	Gdx.audio.newSound(Gdx.files.internal("../android/assets/Windows Notify System Generic.wav"));
		music = 	Gdx.audio.newMusic(Gdx.files.internal("../android/assets/_vater_ von gerhard gundermann_bild_ ute donner.mp3"));
	
		music.setVolume((float)0.5);//repmannest
		music.play();//repmannest
	}

	void calculate () {
		
		if(Gdx.input.isTouched()){
			x_inc[1] = -x_inc[1];
			y_inc[1] = -y_inc[1];
			x[0]=Gdx.input.getX();
			y[0]=Gdx.graphics.getHeight() - Gdx.input.getY();
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.D )){
			img[0]=doerk;
			img[1]=doerk;
		}
		else if(Gdx.input.isKeyJustPressed(Keys.J )){
			img[0]=jagla;
			img[1]=jagla;
		}
		else if(Gdx.input.isKeyJustPressed(Keys.S )){
			Texture	tmp_img=img[0];	
			img[0]=img[1];
			img[1]=tmp_img;
		}
		else if(Gdx.input.isKeyJustPressed(Keys.SPACE)){
			if(music.isPlaying())
				music.pause();
			else
				music.play();
		}
		else if(Gdx.input.isKeyJustPressed(Keys.ANY_KEY )){
//			Texture	mp_img=img[0];	
			img[0]=doerk;
			img[1]=jagla;
		}
		
		for(int i=0;i<2;i++){
			if (x[i]+x_inc[i] >= Gdx.graphics.getWidth() - img[i].getWidth()) {
				sound[i].play(1,1,1);
				x_inc[i] = -x_inc[i];
				red = 1;
			} else if (x[i]+x_inc[i] <= 0) {
				sound[i].play(1,1,-1);
				x_inc[i] = -x_inc[i];
				red = 0;
			}
			if (y[i]+y_inc[i] >= Gdx.graphics.getHeight() - img[i].getHeight()) {
				y_inc[i] = -y_inc[i];
				green = 1;
			} else if (y[i]+y_inc[i] <= 0) {
				y_inc[i] = -y_inc[i];
				green = 0;
			}
			x[i]+=x_inc[i];
			y[i]+=y_inc[i];
		}

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(red, green, blue, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		calculate ();
		
		batch.begin();
		for(int i=0;i<2;i++){
			batch.draw(img[i], x[i], y[i]);
		}

		batch.end();
	}
}
