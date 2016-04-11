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
import com.hot.ball.hotball.controller.ai.util.Util;
import com.hot.ball.hotball.universe.player.Player;
import java.util.Set;

/**
 *
 * @author Dromlius
 */
public class Center extends Behavior {

    @Override
    public Vector action(Player p) {
        Set<VoronoiArea> possibleFields = Analysis.get().processQuery(new Analysis.Filter[]{Filter.canBePassedTo, Filter.noEnemies0, Filter.noEnemies1,}, p);

        Position bestPoint = null;
        int bestPossiblePasses = -1;
        int bestMobRating = -1;

        for (VoronoiArea va : possibleFields) {
            int possiblePasses = 0;
            for (Player ally : p.getTeam().getMembers()) {
                if (p.equals(ally)) {
                    continue;
                }
                if (Util.canThrow(ally, va.getCenter())) {
                    possiblePasses++;
                }
            }

            if (possiblePasses < bestPossiblePasses) {
                continue;
            }
            int mobRating = va.getMobilityRating(p.getTeam());
            if (possiblePasses > bestPossiblePasses || bestMobRating < mobRating) {
                bestPoint = va.getCenter();
                bestMobRating = mobRating;
                bestPossiblePasses = possiblePasses;
            }
        }
        return goTo(p,tacticalMovement(p, bestPoint, new Tactic[]{Tactic.noEnemies}));
    }

}
