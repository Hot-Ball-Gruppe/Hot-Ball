/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
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
    
    private final Sound timeBreak;
    private	final	Sound realTime;
    
    private long realtimeID;
    
    private AudioManager() {
        timeBreak=Court.get().getThemeMusic();
        realTime=Gdx.audio.newSound(Gdx.files.internal("aud/realtime.wav"));
    }

    public void start() {

        timeBreak.loop();
        realtimeID = realTime.loop();
     //   realTime.play();
      //  System.out.println(realTime.isPlaying());
      /*  System.out.println("Audio start "+(realTime==null)+" "+(timeBreak==null));
        System.out.println(realTime.getVolume());
        System.out.println(realTime.toString());
        System.out.println(realTime.isPlaying());*/
    }
    
    public void pause(){
        realTime.setVolume(realtimeID, 0);
      //  System.out.println("Pause");
    }
    
    public void resume(){
    	 realTime.setVolume(realtimeID, 1); //    realTime.setVolume(1);
        System.out.println("Resume");
    }
    
    public void stop(){
     //   System.out.println("ST:"+realTime.isPlaying());
        timeBreak.stop();
        timeBreak.dispose();
        realTime.stop();
        realTime.dispose();
        System.out.println("STOP");
    }
}
