package com.hot.ball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.analysis.Analysis;
import com.hot.ball.hotball.logic.LogicCore;
import com.hot.ball.hotball.ui.AudioManager;
import com.hot.ball.hotball.ui.BallScoreAnimation;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.ui.KeyBinding;
import com.hot.ball.hotball.ui.UserInput;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.collision.CollisionModell;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

//repmanntest
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.hot.ball.game.Assets;
import com.hot.ball.screens.*;
//repmanntest

//repmanntest public class HotballGame extends ApplicationAdapter {
public class HotballGame extends Game {//repmanntest
    private boolean drawing;

    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void create() {
        Court.generate(Court.Type.Wood);
        CollisionModell.generate();

        Ball.create(new Position.DoublePosition(Court.COURT_WIDTH / 2, Court.COURT_HEIGHT / 2));
        Team.generate(new Player[]{Player.Felix, Player.Friedrich, Player.Adrian}, new Player[]{Player.Leo, Player.Thomas, Player.Patryk}, Court.get().getLeftBasket(), Court.get().getRightBasket());
        //  Team.generate(new Player[]{Player.Felix}, new Player[]{}, Court.get().getLeftBasket(), Court.get().getRightBasket());

        UserInput.create(Gdx.input, KeyBinding.GDX_WASD, UserInput.ControlMode.ScreenRelational);
        HumanController.create();

        Player.setHumanPlayer(Team.BLUE.getMembers()[0]);

        for (Player p : Team.BLUE.getMembers()) {
            System.out.println(p.getName());
        }

        Analysis.create("unweightedField");

        LogicCore.create();
        AudioManager.create();
        LogicCore.get().start();
        AudioManager.get().start();
        Graphics.create();
        BallScoreAnimation.supidLibGDX();
        System.out.println("SETUP COMPLETE");
      //repmanntest        drawing = true;
        //   tst = new Texture(Gdx.files.internal("res/scoreAnimation/sa_f0.png"));
    
        setScreen(new MenuScreen(this));//repmanntest
    }
/*repmanntest
    // Texture tst;
    @Override
    public void render() {
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
repmanntest*/
}
