package com.hot.ball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import help.math.Position;
import hotball.controller.HumanController;
import hotball.controller.ai.AIController;
import hotball.logic.LogicCore;
import hotball.universe.GameObject;
import hotball.universe.ball.Ball;
import hotball.universe.collision.CollisionModell;
import hotball.universe.player.Player;
import hotball.universe.player.Team;
import hotball.universe.player.TeamColor;

public class HotballGame extends ApplicationAdapter {

    private SpriteBatch batch;

    @Override
    public void create() {
        CollisionModell.generate();
        HumanController.create();
        Team redTeam = new Team(TeamColor.Red);
        Team blueTeam = new Team(TeamColor.Blue);
        Player ai2 = new Player(null, redTeam, new AIController(), new Position.DoublePosition(460, 500));
      //  Player player = new Player(null, blueTeam, HumanController.get(), new Position.DoublePosition(400, 400));
        Player ai = new Player(null, blueTeam, new AIController(), new Position.DoublePosition(300, 500));
        Ball.create(ai2);
        LogicCore.create();
        LogicCore.get().start();
        
        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        batch.begin();
        for(GameObject toDraw:GameObject.ALL_GAMEOBJECTS){
            toDraw.draw(batch);
        }
        batch.end();
    }
}
