/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.analysis;

import com.hot.ball.hotball.universe.player.Player;

/**
 *
 * @author Inga
 */
public enum Tactic {

    noEnemies {

                @Override
                public double rate(VoronoiArea toRate, Player player) {
                    double enemiesTZOn = 0;
                    double enemiesTZNext = 0;
                    int allyOn=0;
                    for (Player occupant : toRate.getOccupants()) {
                        if (player.getTeam().getOpponent().isMember(occupant)) {
                            enemiesTZOn += occupant.getTackleZoneSize();
                        }else{
                            allyOn++;
                        }
                    }

                    for (VoronoiArea neighbor : toRate.getConnected()) {
                        for (Player occupant : neighbor.getOccupants()) {
                            if (player.getTeam().getOpponent().isMember(occupant)) {
                                enemiesTZNext += occupant.getTackleZoneSize();
                            }
                        }
                    }

                    return 10*Math.PI * (2*enemiesTZOn + enemiesTZNext)+allyOn*64;
                }
            };

    public abstract double rate(VoronoiArea toRate, Player player);

}
