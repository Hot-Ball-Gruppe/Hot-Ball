/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.collision;

import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.logic.GameLoop;
import com.hot.ball.hotball.logic.LogicCore;
import com.hot.ball.hotball.ui.BallScoreAnimation;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.BallState;
import com.hot.ball.hotball.universe.ball.InAir;
import com.hot.ball.hotball.universe.court.Basket;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;

/**
 *
 * @author Dromlius
 */
public class CollisionModell {

    private static CollisionModell cm;

    public static void generate() {
        if (cm != null) {
            throw new RuntimeException("CollisionModell already created");
        }
        cm = new CollisionModell();
    }

    public static CollisionModell get() {
        if (cm == null) {
            throw new RuntimeException("CollisionModell not yet created");
        }
        return cm;
    }

    private CollisionModell() {
    }

    public void checkCollision(GameObject go, double timeDiff) {
        Position.DoublePosition pos = go.getPosition();
        double x = pos.getX();
        double y = pos.getY();
        double size = go.getSize();
        double dx = go.getCurrentVelocity().getdX() * timeDiff;
        double dy = go.getCurrentVelocity().getdY() * timeDiff;
        double newX = x + dx;
        double newY = y + dy;

        if (go instanceof Ball) {
            if (newX  < -Court.OFFSET_X && dx < 0) {
                //go.getPosition().setX(X_BOUND - newX);
                go.getCurrentVelocity().flipdX();
            }
            if (newX  > Court.COURT_WIDTH + Court.OFFSET_X && dx > 0) {
                //  go.getPosition().setX(newX - X_BOUND);
                go.getCurrentVelocity().flipdX();
            }
        } else {
            if (newX  < 0 && dx < 0) {
                //go.getPosition().setX(X_BOUND - newX);
                go.getCurrentVelocity().flipdX();
            }
            if (newX > Court.COURT_WIDTH && dx > 0) {
                //  go.getPosition().setX(newX - X_BOUND);
                go.getCurrentVelocity().flipdX();
            }
        }
        if (newY < 0 && dy < 0 || newY  > Court.COURT_HEIGHT && dy > 0) {
            go.getCurrentVelocity().flipdY();
        }
        for (GameObject other : GameObject.ALL_GAMEOBJECTS) {
            if (go.equals(other)) {
                continue;
            }
            if (pos.getDistance(other.getPosition()) < size + other.getSize()) {
                collide(go, other);
            }
        }

    }

    private void collide(GameObject go, GameObject other) {
        if (go instanceof Player && other instanceof Player){// && !((Player)go).getTeam().isMember((Player)other)) {
            go.setCurrentVelocity(new Vector(go.getCurrentVelocity().getLength(), other.getPosition().angleBetween(go.getPosition()), null));
            other.setCurrentVelocity(new Vector(other.getCurrentVelocity().getLength(), go.getPosition().angleBetween(other.getPosition()), null));
        }
        if (go instanceof Ball && other instanceof Basket) {
            Basket basket = (Basket) other;
            BallState bs = ((Ball) go).getState();
            if (basket.getLastAttack() == bs.getID()) {
                return;
            }

            InAir state = (InAir) bs;

            boolean success = Math.random() < state.getChance();

            GameLoop.get().addBlockCondition(new BallScoreAnimation(state.getThrower().getTeam(), state.getChance(), success));
            basket.setLastAttack(bs.getID());
            state.setThrower(null);
            if (success) {
                LogicCore.get().reset();
            }
        }
    }
}
