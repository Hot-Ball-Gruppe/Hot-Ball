/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.hot.ball.hotball.logic.BlockCondition;
import com.hot.ball.hotball.logic.LogicCore;
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Dromlius
 */
public class BallScoreAnimation implements Overlay, BlockCondition {

    private final static Animation SCORE_UNDECIDED;
    private final static Animation SCORE_WIN;
    private final static Animation SCORE_FAIL;

    private final static Texture[] DIGIT_FIRST;
    private final static Texture[] DIGIT_SECOND;

    private final static float FRAME_DURATION = 0.125f;

    static {
        TextureRegion[] undecided = new TextureRegion[10];
        for (int i = 0; i < undecided.length; i++) {
            undecided[i] = new TextureRegion(new Texture(Gdx.files.internal("res/scoreAnimation/throw" + (i + 1) + ".png")));
        }
        TextureRegion[] win = new TextureRegion[12];
        for (int i = 0; i < win.length; i++) {
            win[i] = new TextureRegion(new Texture(Gdx.files.internal("res/scoreAnimation/hit" + (i + 1) + ".png")));
        }
        TextureRegion[] fail = new TextureRegion[6];
        for (int i = 0; i < fail.length; i++) {
            fail[i] = new TextureRegion(new Texture(Gdx.files.internal("res/scoreAnimation/miss" + (i + 1) + ".png")));
        }
        SCORE_UNDECIDED = new Animation(FRAME_DURATION, undecided);
        SCORE_WIN = new Animation(FRAME_DURATION, win);
        SCORE_FAIL = new Animation(FRAME_DURATION, fail);

        DIGIT_FIRST = new Texture[10];
        DIGIT_SECOND = new Texture[10];
        for (int i = 0; i < DIGIT_FIRST.length; i++) {
            DIGIT_FIRST[i] = new Texture(Gdx.files.internal("res/scoreAnimation/sa_" + i + "_.png"));
            DIGIT_SECOND[i] = new Texture(Gdx.files.internal("res/scoreAnimation/sa__" + i + ".png"));
        }
    }

    public static void supidLibGDX() {
    }
    ;
    //
    private final Team thrower;
    private final int probability;
    private final boolean success;

    private final float tensionTime;

    @SuppressWarnings("LeakingThisInConstructor")
    public BallScoreAnimation(Team thrower, double probability, boolean success) {
        this.thrower = thrower;
        this.probability = Math.max(0, Math.min(99, (int) (probability * 100)));
        this.success = success;

        tensionTime = (float) (4 * FRAME_DURATION + Math.random() * 2 * FRAME_DURATION);
        Graphics.get().addOverlay(this);
    }

    private float ellapsedTime;

    @Override
    public boolean draw(Graphics g, double time) {
        ellapsedTime += time;
        if (ellapsedTime < tensionTime) {
            g.drawImageScreenCenter(SCORE_UNDECIDED.getKeyFrame(ellapsedTime, true));
        } else if (success) {
            g.drawImageScreenCenter(SCORE_WIN.getKeyFrame(ellapsedTime - tensionTime, false));
            boolean animationFinished = SCORE_WIN.isAnimationFinished(ellapsedTime - tensionTime);
            done = animationFinished;
        } else {
            g.drawImageScreenCenter(SCORE_FAIL.getKeyFrame(ellapsedTime - tensionTime, false));
            boolean animationFinished = SCORE_FAIL.isAnimationFinished(ellapsedTime - tensionTime);
            done = animationFinished;
        }
        g.drawImageScreenCenter(DIGIT_FIRST[probability / 10]);
        g.drawImageScreenCenter(DIGIT_SECOND[probability % 10]);
        return done;
    }

    private boolean done = false;

    @Override
    public boolean isBlocking() {
        return !done;
    }

    @Override
    public boolean isPermanent() {
        return false;
    }

    @Override
    public void end() {
        if (success) {
            LogicCore.get().reset();
            switch (thrower.getColor()) {
                case Blue:
                    LogicCore.get().blueScore++;
                    break;
                case Red:
                    LogicCore.get().redScore++;
                    break;
            }
        }
    }

}
