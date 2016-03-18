/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.hot.ball.hotball.universe.court.Court;

/**
 *
 * @author Dromlius
 */
public class AudioManager {

    private static AudioManager singleton;

    public static void create() {
        if (singleton == null) {
            singleton = new AudioManager();
            return;
        }
        throw new RuntimeException("AudioManager already created!");
    }

    public static AudioManager get() {
        if (singleton == null) {
            throw new RuntimeException("AudioManager not yet created!");
        }
        return singleton;
    }
    
    private final Music timeBreak,realTime;
    
    private AudioManager() {
        timeBreak=Court.get().getThemeMusic();
        realTime=Gdx.audio.newMusic(Gdx.files.internal("aud/realTime.wav"));
        realTime.setLooping(true);
    }

    public void start() {
      //  timeBreak.play();
    //    realTime.play();
    }
    
    public void pause(){
        realTime.setVolume(0);
    }
    
    public void resume(){
        realTime.setVolume(1);
    }
    
    public void stop(){
        timeBreak.stop();
        timeBreak.dispose();
        realTime.stop();
        realTime.dispose();
        
    }
}
