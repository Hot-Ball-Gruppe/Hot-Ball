package com.hot.ball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.AIController;
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

public class HotballGame extends ApplicationAdapter {

    @Override
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void create() {
        Court.generate(Court.Type.Wood);

        CollisionModell.generate();
        UserInput.create(Gdx.input, KeyBinding.GDX_WASD, UserInput.ControlMode.ScreenRelational);
      //  HumanController.create();
        //  Team redTeam = new Team(TeamColor.Red);
        // Team blueTeam = new Team(TeamColor.Blue);
        //   redTeam.setOpponent(blueTeam);
        // blueTeam.setOpponent(redTeam);
        for (int i = 0; i < 3; i++) {
            new Player(null, Team.RED, new AIController(), new Position.DoublePosition(1200 - 70, i * 100 + 100));
            new Player(null, Team.BLUE, new AIController(), new Position.DoublePosition(70, i * 100 + 100));
        }
       // new Player(null, Team.RED, new AIController(), new Position.DoublePosition(1200 - 70, 300));
       // Player player = new Player(null, Team.BLUE, HumanController.get(), new Position.DoublePosition(70, 300));
        //   Player ai = new Player(null, blueTeam, new AIController(), new Position.DoublePosition(300, 500));
        Ball.create(Team.BLUE.getMembers().get(0));
        LogicCore.create();
        AudioManager.create();
        LogicCore.get().start();
        AudioManager.get().start();
        Graphics.create();
        BallScoreAnimation.supidLibGDX();
     //   tst = new Texture(Gdx.files.internal("res/scoreAnimation/sa_f0.png"));
    }

   // Texture tst;

    @Override
    public void render() {
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
