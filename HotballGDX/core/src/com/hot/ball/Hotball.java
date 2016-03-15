package com.hot.ball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
//import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

import java.io.File;
/*
public class Hotball implements ApplicationListener {
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;
	private Sprite sprite;
	
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
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, h / w);
		batch = new SpriteBatch();

		texture = new Texture(Gdx.files.internal("../android/assets/Basketball.Fel.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		TextureRegion region = new TextureRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());

		sprite = new Sprite(region);
		sprite.setSize(0.9f, 0.9f * sprite.getHeight() / sprite.getWidth());
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		sprite.setPosition(-sprite.getWidth() / 2, -sprite.getHeight() / 2);

		doerk = 	new Texture("../android/assets/300px-DirkNowitzki.jpg");
		img[0] = 	doerk;
		jagla = 	new Texture("../android/assets/270px-JanJagla.jpg");
		
		sound[0] = 	Gdx.audio.newSound(Gdx.files.internal("../android/assets/Windows Logoff Sound.wav"));
		sound[1] = 	Gdx.audio.newSound(Gdx.files.internal("../android/assets/Windows Notify System Generic.wav"));
		music = 	Gdx.audio.newMusic(Gdx.files.internal("../android/assets/_vater_ von gerhard gundermann_bild_ ute donner.mp3"));
	
		music.setVolume((float)0.5);//repmannest
		music.play();//repmannest
	
}

	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
	}

	@Override
	public void render () {

		calculate ();
		
		Gdx.gl.glClearColor(red, green, blue, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for(int i=0;i<2;i++){
			batch.draw(img[i], x[i], y[i]);
		}
		sprite.draw(batch);
		batch.end();

}

	@Override
	public void resize (int width, int height) {
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}
	

}
*/

public class Hotball extends ApplicationAdapter {
	SpriteBatch batch;
	Texture doerk;
	Texture jagla;
	Texture[]	img={null,null,null};
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
               // File f= new File("");
                //System.out.println(f.getAbsolutePath());
		doerk = 	new Texture("300px-DirkNowitzki.jpg");
		img[0] = 	doerk;
		jagla = 	new Texture("270px-JanJagla.jpg");
		
		img[1] = 	jagla;
		img[2] = 	new Texture("Basketball.Fel.png");
		sound[0] = 	Gdx.audio.newSound(Gdx.files.internal("Windows Logoff Sound.wav"));
		sound[1] = 	Gdx.audio.newSound(Gdx.files.internal("Windows Notify System Generic.wav"));
		music = 	Gdx.audio.newMusic(Gdx.files.internal("_vater_ von gerhard gundermann_bild_ ute donner.mp3"));
	
		music.setVolume((float)0.5);//repmannest
		music.play();//repmannest
	}


	@Override
	public void render () {
		Gdx.gl.glClearColor(red, green, blue, alpha);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		calculate ();
		
		batch.begin();
		batch.draw(img[2], 0, 0);
		for(int i=0;i<2;i++){
			batch.draw(img[i], x[i], y[i]);
		}

		batch.end();
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
//			sprite.rotate90(true);

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
}
