package com.hot.ball;

import com.badlogic.gdx.Gdx;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.analysis.Analysis;
import com.hot.ball.hotball.logic.LogicCore;
import com.hot.ball.hotball.ui.AudioManager;
import com.hot.ball.hotball.ui.BallScoreAnimation;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.ui.KeyBinding;
import com.hot.ball.hotball.ui.UserInput;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.collision.CollisionModell;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

//repmanntest
import com.badlogic.gdx.Game;
import com.hot.ball.hotball.controller.ai.roles.BallCarrierAI;
import com.hot.ball.screens.*;
//repmanntest

//repmanntest public class HotballGame extends ApplicationAdapter {
public class HotballGame extends Game {//repmanntest
    private boolean drawing;

    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void create() {
        
    
        setScreen(new MenuScreen(this));//repmanntest
    }

}
