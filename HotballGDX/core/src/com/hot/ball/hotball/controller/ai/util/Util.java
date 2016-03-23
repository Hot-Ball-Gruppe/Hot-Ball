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
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;

/**
 *
 * @author Inga
 */
public class Util {

    public static void main(String[] args) {
        
        
    }

    public static double radiusFkt(Position k, Position p, Vector r) {
        // Math.sqrt(d);
        double bruch = (-k.getX() * r.getdX() + p.getX() * r.getdX() - k.getY() * r.getdY() + p.getY() * r.getdY()) / (Math.pow(r.getdX(), 2) + Math.pow(r.getdY(), 2));
        double radius = Math.sqrt(Math.pow((p.getX() - (k.getX() + bruch * r.getdX())), 2) + Math.pow((p.getY() - (k.getY() + bruch * r.getdY())), 2));
        return radius;
    }

    public static DoublePosition bandenFkt(Position s, Position k, boolean isUpper) {
        double xwert;
        double wert = Court.COURT_HEIGHT;
        if (isUpper) {
            xwert = k.getX() + ((wert - k.getY()) / (1.5 * wert - k.getY())) * (s.getX() - k.getX());
        } else {
            xwert = k.getX() + ((-k.getY()) / (-0.5 * wert - k.getY())) * (s.getX() - k.getX());
        }
        return new DoublePosition(xwert, isUpper ? Court.COURT_HEIGHT : 0);
    }
    
    public static BandenSeite BandenCheckFKT(Player player, Position ziel){
        boolean upper;
        boolean oben = true;
        boolean unten = true;
        BandenSeite result;
        upper = player.getPosition().getY()>Court.COURT_HEIGHT/2;
        for (Player opponent : player.getTeam().getOpponent().getMembers()) {
            Position u = bandenFkt(ziel, player.getPosition(), true);
            if (radiusFkt(player.getPosition(), opponent.getPosition(), new Vector(u.getX()-player.getPosition().getX(), u.getY()-player.getPosition().getY()))>opponent.getTackleZoneSize()){
                if (radiusFkt(u, opponent.getPosition(), new Vector(ziel.getX()-u.getX(), ziel.getY()-u.getY()))>opponent.getTackleZoneSize()){
                    continue;
                }else{
                    oben =  false;
                }
            }else{
                oben = false;
            }
        }
        for (Player opponent : player.getTeam().getOpponent().getMembers()) {
            Position u = bandenFkt(ziel, player.getPosition(), false);
            if (radiusFkt(player.getPosition(), opponent.getPosition(), new Vector(u.getX()-player.getPosition().getX(), u.getY()-player.getPosition().getY()))>opponent.getTackleZoneSize()){
                if (radiusFkt(u, opponent.getPosition(), new Vector(ziel.getX()-u.getX(), ziel.getY()-u.getY()))>opponent.getTackleZoneSize()){
                    continue;
                }else{
                    unten =  false;
                }
            }else{
                unten = false;
            }
        }
        if (upper){
            if(oben){
                return BandenSeite.oben;
            }if(unten){
                return BandenSeite.unten;
            }
            else{
            return BandenSeite.keins;
            }      
        }else{
            if (unten){
                return BandenSeite.unten;
            }if(oben){
                return BandenSeite.oben;
            }else{
                return BandenSeite.keins;
            }
        }
    }
    
     public static BandenSeite DoppelBandenCheckFKT(Player player){
        boolean upper;
        boolean oben = true;
        boolean unten = true;
        upper = player.getPosition().getY()>Court.COURT_HEIGHT/2;
        Position ziel = doppelbandeHinten(player, true);
        Position u = doppelBandenFkt(player, true);
        for (Player opponent : player.getTeam().getOpponent().getMembers()) {
            if (radiusFkt(player.getPosition(), opponent.getPosition(), new Vector(u.getX()-player.getPosition().getX(), u.getY()-player.getPosition().getY()))>opponent.getTackleZoneSize()){
                if (radiusFkt(u, opponent.getPosition(), new Vector(ziel.getX()-u.getX(), ziel.getY()-u.getY()))>opponent.getTackleZoneSize()){
                    continue;
                }else{
                    oben =  false;
                }
            }else{
                oben = false;
            }
        }
        ziel = doppelbandeHinten(player, false);
        u = doppelBandenFkt(player, false);
        for (Player opponent : player.getTeam().getOpponent().getMembers()) {
            if (radiusFkt(player.getPosition(), opponent.getPosition(), new Vector(u.getX()-player.getPosition().getX(), u.getY()-player.getPosition().getY()))>opponent.getTackleZoneSize()){
                if (radiusFkt(u, opponent.getPosition(), new Vector(ziel.getX()-u.getX(), ziel.getY()-u.getY()))>opponent.getTackleZoneSize()){
                    continue;
                }else{
                    unten =  false;
                }
            }else{
                unten = false;
            }
        }
        if (upper){
            if(oben){
                return BandenSeite.oben;
            }if(unten){
                return BandenSeite.unten;
            }
            else{
            return BandenSeite.keins;
            }      
        }else{
            if (unten){
                return BandenSeite.unten;
            }if(oben){
                return BandenSeite.oben;
            }else{
                return BandenSeite.keins;
            }
        }
    }

    public static boolean canThrow(Player player, Position target) {
        double dist = player.getPosition().getDistance(target);
        if (dist < player.getMaxThrowDist()) {
            //WARNING: THIS DISREGARDS HANDOFFS!!!!!!
            for (Player opponent : player.getTeam().getOpponent().getMembers()) {
                if (ClosestToStrecke(player.getPosition(), target, opponent.getPosition()).getDistance(opponent.getPosition()) < opponent.getTackleZoneSize()) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static Position ClosestToStrecke(Position a, Position b, Position p) {
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
        Player bc = Ball.get().getBallCarrier();
        if (bc.getPosition().getY() > Court.COURT_HEIGHT / 2) {
            for (Player gegner : bc.getTeam().getOpponent().getMembers()) {

            }
        }
        return null;
    }

    public static Position doppelBandenFkt(Player pl, boolean isUpper) {
        Position s = pl.getTeam().getAttacking().getPosition();
        double hBandeX;
        if (Team.BLUE.isMember(pl)) {
            hBandeX = Court.COURT_WIDTH + Court.OFFSET_X;
        } else {
            hBandeX = - Court.OFFSET_X;
        }
        Position k = pl.getPosition();
        double p = k.getX()- hBandeX;
        if (!isUpper) {
             p+= (-k.getY() / (-s.getY() - k.getY())) * (-s.getX() + 2 * hBandeX - k.getX()); 
            return new Position.FinalPosition(hBandeX + p, 0);
        } else {
            p+= ((2*s.getY()-k.getY()) / (3*s.getY() - k.getY())) * (-s.getX() + 2 * hBandeX - k.getX());
            return new Position.FinalPosition(hBandeX + p, 2*s.getY());
        }
    }
    
    public static Position doppelbandeHinten(Player pl, boolean isUpper){
        Position s = pl.getTeam().getAttacking().getPosition();
        double hBandeX;
        if (Team.BLUE.isMember(pl)) {
            hBandeX = Court.COURT_WIDTH + Court.OFFSET_X;
        } else {
            hBandeX = - Court.OFFSET_X;
        }
        Position k = pl.getPosition();
        
        Position.FinalPosition secondBandenPkt;
        if(!isUpper){
            double p = k.getY()+((hBandeX-k.getX())/(-s.getX()+2*hBandeX-k.getX()))*(-s.getY()-k.getY());
            secondBandenPkt = new Position.FinalPosition(hBandeX, -p);
        }else{
            double p = k.getY()-2*s.getY()+((hBandeX-k.getX())/(-s.getX()+2*hBandeX-k.getX()))*(3*s.getY()-k.getY());
            secondBandenPkt = new Position.FinalPosition(hBandeX,2*s.getY()-p);
        }
        //Von PLayerPos to bande mit WufrTextFkt
        //Von bande to 2ndBande radiusFkt iteriert über alle gegnas
        return secondBandenPkt;
        
    }
}
