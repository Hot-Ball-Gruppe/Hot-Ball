/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.controller.ai.analysis.Tactic;
import com.hot.ball.hotball.controller.ai.util.Util;
import com.hot.ball.hotball.universe.court.Basket;
import com.hot.ball.hotball.universe.court.Court;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;

/**
 *
 * @author Inga
 */
public class BallCarrierAI extends Behavior {

    private static BallCarrierAI singleton;

    public static void create() {
        if (singleton != null) {
            throw new RuntimeException("Analysis already created!");
        }
        singleton = new BallCarrierAI();
    }

    public static BallCarrierAI get() {
        if (singleton == null) {
            throw new RuntimeException("Analysis not yet created!");
        }
        return singleton;
    }

    private final static double CHANCETOHITTHRESHOLD = 0.60;

    @Override
    public Vector action(Player p) {
        Basket attacking = p.getTeam().getAttacking();
        //try To Score
        if (p.getChanceToHit() > CHANCETOHITTHRESHOLD + Math.random() * 0.3) {
            if (Util.canThrow(p, attacking.getPosition())) {
                p.throwBall(attacking.getPosition());
                return Vector.NULL_VECTOR;
            }
            Util.BandenSeite bande = Util.BandenCheckFKT(p, attacking.getPosition());
            Util.BandenSeite bande2 = Util.DoppelBandenCheckFKT(p);
            boolean isUpper = p.getPosition().getY() > Court.COURT_HEIGHT / 2;
            if (isUpper && bande2 == Util.BandenSeite.oben && bande != Util.BandenSeite.oben) {
                p.throwBall(Util.doppelBandenFkt(p, true));
                return Vector.NULL_VECTOR;
            }

            if (!isUpper && bande2 == Util.BandenSeite.unten && bande != Util.BandenSeite.unten) {
                p.throwBall(Util.doppelBandenFkt(p, false));
                return Vector.NULL_VECTOR;
            }

            if (bande != Util.BandenSeite.keins) {
                p.throwBall(Util.bandenFkt(attacking.getPosition(), p.getPosition(), bande == Util.BandenSeite.oben));
                return Vector.NULL_VECTOR;

            }

            if (bande2 != Util.BandenSeite.keins) {
                p.throwBall(Util.doppelBandenFkt(p, bande2 == Util.BandenSeite.oben));
                return Vector.NULL_VECTOR;
            }
        }
        //can Someone else score
        for (Player ally : p.getTeam().getMembers()) {
            if (p.equals(ally)) {
                continue;
            }
            if (ally.getChanceToHit() > CHANCETOHITTHRESHOLD + Math.random() * 0.3) {

                Util.BandenSeite singleBand = Util.BandenCheckFKT(ally, attacking.getPosition());
                Util.BandenSeite doubleBand = Util.DoppelBandenCheckFKT(ally);

                if (Util.canThrow(ally, attacking.getPosition()) || singleBand != Util.BandenSeite.keins || doubleBand != Util.BandenSeite.keins) {

                    if (Util.canThrow(p, ally.getPosition())) {
                        p.throwBall(attacking.getPosition());
                        return Vector.NULL_VECTOR;
                    }

                    Util.BandenSeite bande = Util.BandenCheckFKT(p, ally.getPosition());
                    if (bande != Util.BandenSeite.keins) {
                        p.throwBall(Util.bandenFkt(ally.getPosition(), p.getPosition(), bande == Util.BandenSeite.oben));
                        return Vector.NULL_VECTOR;
                    }
                }
            }
        }
        for (Zone z : p.getInterferingZones()) {
            if (z instanceof TackleZone && p.getTeam().getOpponent().isMember(((TackleZone) z).getPlayer())) {
                Player forwardAlly = null;
                double bestForwardDist = Double.POSITIVE_INFINITY;
                for (Player ally : p.getTeam().getMembers()) {
                    if (p.equals(ally)) {
                        continue;
                    }
                    if (Util.canThrow(p, ally.getPosition())) {
                        double forwardDist = ally.getPosition().getDistance(attacking.getPosition());
                        if (forwardDist < bestForwardDist) {
                            bestForwardDist = forwardDist;
                            forwardAlly = ally;
                        }
                    }
                }
                if (forwardAlly != null) {
                    p.throwBall(forwardAlly.getPosition());
                    return Vector.NULL_VECTOR;
                }

                double bestAngleAbs = Double.POSITIVE_INFINITY;
                Player bestAlly = null;
                double attackAngle = ((TackleZone) z).getPlayer().getPosition().angleBetween(p.getPosition());
                attackAngle += (attackAngle < 0) ? 2 * Math.PI : 0;
                for (Player ally : p.getTeam().getMembers()) {
                    if (p.equals(ally)) {
                        continue;
                    }
                    double angle = p.getPosition().angleBetween(ally.getPosition());
                    angle += (angle < 0) ? 2 * Math.PI : 0;
                    angle = Math.abs(attackAngle - angle);
                    if (angle < bestAngleAbs) {
                        bestAngleAbs = angle;
                        bestAlly = ally;
                    }
                }
                if (bestAngleAbs < Math.PI / 2) {
                    p.throwBall(bestAlly.getPosition());
                }
            }
        }
        return goTo(p, tacticalMovement(p, attacking.getPosition(), new Tactic[]{Tactic.noEnemies}));

    }
}
