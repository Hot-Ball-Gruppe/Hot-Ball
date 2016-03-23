/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.controller.ai.analysis.Analysis;
import com.hot.ball.hotball.controller.ai.analysis.Analysis.Filter;
import com.hot.ball.hotball.controller.ai.analysis.Tactic;
import com.hot.ball.hotball.controller.ai.analysis.VoronoiArea;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;
import java.util.Set;

/**
 *
 * @author Dromlius
 */
public class Forward extends Behavior {

    @Override
    public Vector action(Player p) {
        for (Zone z : p.getInterferingZones()) {
            if (z instanceof TackleZone) {
                Player tackler = ((TackleZone) z).getPlayer();
                if (p.getTeam().getOpponent().isMember(tackler)) {
                  //  return new Vector(p.getPosition().getX() -tackler.getPosition().getX(), p.getPosition().getY() -tackler.getPosition().getY(), 1);
                }
            }
        }
        Set<VoronoiArea> possibleFields = Analysis.get().processQuery(new Analysis.Filter[]{Filter.canBePassedTo, Filter.noEnemies0, Filter.AggroRatingBetterThanBC, Filter.noEnemies1, Filter.noAllys0, Filter.noAllys1}, p);
        
        Position bestPoint = null;
        int bestAtkValue=-1;
        double bestDistToBCAndBasket=Double.POSITIVE_INFINITY;
        
        for(VoronoiArea va:possibleFields){
            int attackRating = va.getAttackRating(p.getTeam());
            if(attackRating<bestAtkValue){
                continue;
            }
            double dist = va.getCenter().getDistance(Ball.get().getBallCarrier().getPosition()) + va.getCenter().getDistance(p.getTeam().getAttacking().getPosition());
            if(attackRating>bestAtkValue || dist<bestDistToBCAndBasket){
                bestPoint = va.getCenter();
                bestAtkValue = attackRating;
                bestDistToBCAndBasket=dist;
            }
        }
       return goTo(p,tacticalMovement(p, bestPoint, new Tactic[]{Tactic.noEnemies}));
    }

}
