package com.hot.ball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hot.ball.Hotball;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = 	new LwjglApplicationConfiguration();

		config.title = "Hotball";
		config.width = 800;
		config.height = 600;
		config.fullscreen=false;
		config.resizable=false;
		
		new LwjglApplication(new Hotball(), config);
		
	}
}
