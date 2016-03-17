package com.hot.ball;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.AIController;
import com.hot.ball.hotball.logic.LogicCore;
import com.hot.ball.hotball.ui.AudioManager;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.ui.KeyBinding;
import com.hot.ball.hotball.ui.UserInput;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.collision.CollisionModell;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;
import com.hot.ball.hotball.universe.player.TeamColor;

public class HotballGame extends ApplicationAdapter {
    
    private Graphics g;
    private  Texture field ;
    @Override
    public void create() {
        CollisionModell.generate();
        UserInput.create(Gdx.input, KeyBinding.GDX_WASD, UserInput.ControlMode.ScreenRelational);
        HumanController.create();
        Team redTeam = new Team(TeamColor.Red);
        Team blueTeam = new Team(TeamColor.Blue); 
        Player ai1 = new Player(null, redTeam, new AIController(), new Position.DoublePosition(170, 170));
      
        Player ai2 = new Player(null, blueTeam, new AIController(), new Position.DoublePosition(70, 70));
        Player player = new Player(null, blueTeam, HumanController.get(), new Position.DoublePosition(400, 400));
     //   Player ai = new Player(null, blueTeam, new AIController(), new Position.DoublePosition(300, 500));
        Ball.create(player);
        LogicCore.create();
        LogicCore.get().start();
        AudioManager.create();
        AudioManager.get().start();
        field = new Texture(Gdx.files.internal("res/Holzfeld.png"));
        g = new Graphics();
    }
    
    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        g.ready();
        g.drawBackgroungImage(field);
         for(GameObject toDraw:GameObject.ALL_GAMEOBJECTS){
            toDraw.draw(g);
        }
        g.draw();
    }
    
}
