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
import com.hot.ball.hotball.controller.ai.analysis.VoronoiArea;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.zone.TackleZone;
import com.hot.ball.hotball.universe.zone.Zone;

/**
 *
 * @author Dromlius
 */
public class Forward extends Behavior {

    @Override
    public Vector action(Player p) {
        boolean inEnemyTZ = false;
        for (Zone z : p.getInterferingZones()) {
            if (z instanceof TackleZone) {
                Player tackler = ((TackleZone) z).getPlayer();
                if (p.getTeam().getOpponent().isMember(tackler)) {
                    return new Vector(p.getPosition().getX() -tackler.getPosition().getX(), p.getPosition().getY() -tackler.getPosition().getY(), 1);
                }
            }
        }
        Position center = ((VoronoiArea) (Analysis.get().processQuery(new Analysis.Filter[]{Filter.canBePassedTo, Filter.noEnemies0, Filter.AggroRatingBetterThanBC, Filter.noEnemies1, Filter.noAllys0, Filter.noAllys1}, p).toArray()[0])).getCenter();
        return new Vector(center.getX() - p.getPosition().getX(), center.getY() - p.getPosition().getY());
    }

}
