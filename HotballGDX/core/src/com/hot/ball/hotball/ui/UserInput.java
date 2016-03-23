/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.logic.BlockCondition;
import com.hot.ball.hotball.logic.GameLoop;
import com.hot.ball.hotball.logic.LogicCore;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Dromlius
 */
public class UserInput implements InputProcessor, BlockCondition {

    private static UserInput singleton;
    private final ControlMode controlMode;

    

    public static enum ControlMode {
        MouseRelational, ScreenRelational;
    }

    public static void create(Input input, KeyBinding keyBinding, ControlMode controlMode) {
        if (singleton != null) {
            throw new RuntimeException("UserInput already created!");
        }
        singleton = new UserInput(keyBinding, controlMode);
        input.setInputProcessor(singleton);
    }

    public static UserInput get() {
        if (singleton == null) {
            throw new RuntimeException("UserInput not yet created!");
        }
        return singleton;
    }

    private final KeyBinding keyBinding;
    private final boolean[] pressedKeys;
    private final Position.DoublePosition mousePosition;

    private UserInput(KeyBinding keyBinding, ControlMode controlMode) {
        this.keyBinding = keyBinding;
        this.controlMode = controlMode;
        pressedKeys = new boolean[512];
        mousePosition = new Position.DoublePosition(0, 0);
    }

    public boolean isPressed(int keyCode) {
        return pressedKeys[keyCode];
    }

    public Vector getMovementVector() {
        Vector movement = new Vector((pressedKeys[keyBinding.getLeft()] ? (-1) : 0) + (pressedKeys[keyBinding.getRight()] ? (1) : 0),
                (pressedKeys[keyBinding.getUp()] ? (1) : 0) + (pressedKeys[keyBinding.getDown()] ? (-1) : 0));
        movement.setLength(1);

        if (controlMode == ControlMode.MouseRelational) {
            movement.rotate(Player.getHumanPlayer().getPosition().angleBetween(mousePosition) - Math.PI / 2);
        }
        return movement;
    }

    public Position.DoublePosition getMousePosition() {
        return mousePosition;
    }
    //------------------

    @Override
    public boolean keyDown(int keycode) {
        pressedKeys[keycode] = true;
        if (keycode == Keys.ESCAPE) {
            LogicCore.get().reset();
        }

        if (keycode == Keys.TAB && !Ball.get().isControlledBy(Player.getHumanPlayer()))  {
            switchPlayer();
        }
        return true;
    }

    private void switchPlayer() {
        Player closest = null;
        double bestDist = Double.POSITIVE_INFINITY;
        for(Player p:Team.BLUE.getMembers()){
            if(p.equals(Player.getHumanPlayer())){
                continue;
            }
            double dist = p.getPosition().getDistance(Ball.get().getPosition());
            if(dist<bestDist){
                bestDist = dist;
                closest = p;
            }
        }
        Player.setHumanPlayer(closest);
    }

    @Override
    public boolean keyUp(int keycode) {
        pressedKeys[keycode] = false;
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (GameLoop.get().isRunning()) {
            Player.getHumanPlayer().throwBall(mousePosition);
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mousePosition.setX(screenX - Court.OFFSET_X);
        mousePosition.setY(Gdx.graphics.getHeight() - screenY);
      //  System.out.println(mousePosition);
        return true;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
    
    @Override
    public boolean isBlocking() {
       return pressedKeys[Keys.SPACE];
    }

    @Override
    public boolean isPermanent() {
        return true;
    }
}
