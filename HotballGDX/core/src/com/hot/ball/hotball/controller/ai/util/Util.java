/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.util;

import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Position.DoublePosition;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.ball.Controlled;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;

/**
 *
 * @author Inga
 */
public class Util {

    public static void main(String[] args) {
        /*DoublePosition ball = new DoublePosition(2, 2);
         Vector dir = new Vector(1, 3);
         DoublePosition player = new DoublePosition(80, 300);
         DoublePosition korb = new DoublePosition(0, 300);
         System.out.println(bandenFkt(korb, player, false));*/

        DoublePosition a = new DoublePosition(0, 5);
        DoublePosition b = new DoublePosition(10, 5);
        DoublePosition p = new DoublePosition(3, 8);
        System.out.println(ClosestToStrecke(a, b, p));
    }

    public static double radiusFkt(DoublePosition k, DoublePosition p, Vector r) {
        // Math.sqrt(d);
        double bruch = (-k.getX() * r.getdX() + p.getX() * r.getdX() - k.getY() * r.getdY() + p.getY() * r.getdY()) / (Math.pow(r.getdX(), 2) + Math.pow(r.getdY(), 2));
        double radius = Math.sqrt(Math.pow((p.getX() - (k.getX() + bruch * r.getdX())), 2) + Math.pow((p.getY() - (k.getY() + bruch * r.getdY())), 2));
        return radius;
    }

    public static DoublePosition bandenFkt(DoublePosition s, DoublePosition k, boolean isUpper) {
        double xwert;
        double wert = Court.COURT_HEIGHT;
        if (isUpper) {
            xwert = k.getX() + ((wert - k.getY()) / (1.5 * wert - k.getY())) * (s.getX() - k.getX());
        } else {
            xwert = k.getX() - ((wert - k.getY()) / (-0.5 * wert - k.getY())) * (s.getX() - k.getX());
        }
        return new DoublePosition(xwert, isUpper ? Court.COURT_HEIGHT : 0);
    }

    public static boolean canThrow(Player player, DoublePosition target) {
        double dist = player.getPosition().getDistance(target);
        if (dist < player.getMaxThrowDist()) {
            //WARNING: THIS DISREGARDS HANDOFFS!!!!!!
            for(Player opponent:player.getTeam().getOpponent().getMembers()){
                if(ClosestToStrecke(player.getPosition(), target, opponent.getPosition()).getDistance(opponent.getPosition())<opponent.getTackleZoneSize()){
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static Position ClosestToStrecke(DoublePosition a, DoublePosition b, DoublePosition p) {
        final Vector vector = new Vector(b.getX() - a.getX(), b.getY() - a.getY());
        double rad = radiusFkt(a, p, vector);
        vector.rotateClockwise();
        vector.setLength(rad);
        DoublePosition pointV1 = new DoublePosition(p);
        pointV1.addVector(vector);

        DoublePosition pointV2 = new DoublePosition(p);
        pointV2.addVector(vector.multiply(-1));

        DoublePosition point = pointV1.getDistance(a) < pointV2.getDistance(a) ? pointV1 : pointV2;

        double streckenLength = a.getDistance(b);
        if (streckenLength < a.getDistance(point)) {
            return b;
        }
        if (streckenLength < b.getDistance(point)) {
            return a;
        }
        return point;
    }

    public static enum BandenSeite {

        oben, unten, keins;
    }

    public static BandenSeite BandenWurfTestFkt() {
        int k = 1;
        Player bc = ((Controlled) Ball.get().getState()).getBallCarrier();
        if (bc.getPosition().getY() > Court.COURT_HEIGHT / 2) {
            for (Player gegner : bc.getTeam().getOpponent().getMembers()) {

            }
        }
        return null;
    }
}
