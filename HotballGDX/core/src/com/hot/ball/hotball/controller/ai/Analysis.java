/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai;

import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Dromlius
 */
public class Analysis {

    private static Analysis singleton;

    public static void create(String fileHandle) {
        if (singleton != null) {
            throw new RuntimeException("Analysis already created!");
        }
        singleton = new Analysis(fileHandle);
    }

    public static Analysis get() {
        if (singleton == null) {
            throw new RuntimeException("Analysis not yet created!");
        }
        return singleton;
    }

    private Analysis(String fileHandle) {
        BufferedReader isr = new BufferedReader(new InputStreamReader(Gdx.files.internal(fileHandle).read()));
        try {
            String line = isr.readLine();
            while (line != null) {
                //PROCESS
                line = isr.readLine();
            }
        } catch (IOException ioe) {

        }
    }

}
