/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.collision;

import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.universe.GameObject;
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

    private final static int xBound = 1200;
    private final static int yBound = 600;

    public void checkCollision(GameObject go, double timeDiff) {
        Position.DoublePosition pos = go.getPosition();
        double x =pos.getX();
        double y = pos.getY();
        double size = go.getSize();
        double dx = go.getCurrentVelocity().getdX()*timeDiff;
        double dy = go.getCurrentVelocity().getdY()*timeDiff;
        double newX=x+dx;
        double newY=y+dy;
        
        if(newX-size<0&&dx<0 || newX+size>xBound && dx>0){
            go.getCurrentVelocity().flipdX();
        }
        
         if(newY-size<0&&dy<0 || newY+size>yBound && dy>0){
            go.getCurrentVelocity().flipdY();
        }
         
         for(GameObject other:GameObject.ALL_GAMEOBJECTS){
             if(go.equals(other)){
                 continue;
             }
             if(pos.getDistance(other.getPosition())<size+other.getSize()){
                 collide(go,other);
             }
         }

    }

    private void collide(GameObject go, GameObject other) {
        if(go instanceof Player && other instanceof Player){
            go.setCurrentVelocity(new Vector(go.getCurrentVelocity().getLength(), other.getPosition().angleBetween(go.getPosition()), null));
            other.setCurrentVelocity(new Vector(other.getCurrentVelocity().getLength(), go.getPosition().angleBetween(other.getPosition()), null));
        }
    }
}
