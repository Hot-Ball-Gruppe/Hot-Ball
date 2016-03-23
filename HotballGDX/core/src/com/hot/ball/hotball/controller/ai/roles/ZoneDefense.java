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
import java.util.Set;

/**
 *
 * @author Dromlius
 */
public class ZoneDefense extends Behavior {

    @Override
    public Vector action(Player p) {
        Set<VoronoiArea> possibleFields = Analysis.get().processQuery(new Analysis.Filter[]{Filter.isDefRatingBetterThanBC, Filter.canBCScoreIfImThere, Filter.noAllys0, Filter.noAllys1}, p);

        Position bestPoint = null;
        double bestDistToBCAndBasket = Double.POSITIVE_INFINITY;

        for (VoronoiArea va : possibleFields) {
            double dist = va.getCenter().getDistance(Ball.get().getBallCarrier().getPosition()) + va.getCenter().getDistance(p.getTeam().getAttacking().getPosition());
            if (dist < bestDistToBCAndBasket) {
                bestPoint = va.getCenter();
                bestDistToBCAndBasket = dist;
            }
        }
        return goTo(p, bestPoint);
    }

}
