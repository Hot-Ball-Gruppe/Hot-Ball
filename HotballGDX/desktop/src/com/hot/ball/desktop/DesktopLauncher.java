package com.hot.ball.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hot.ball.HotballGame;

public class DesktopLauncher {

    public static void main(String[] arg) {
         
    	LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.title = "Hotball";
        config.width = 1600;
        config.height = 600;
        config.fullscreen = false;
        config.resizable = false;
        // TestFrame.main(arg);
        new LwjglApplication(new HotballGame(), config);

    }
}

