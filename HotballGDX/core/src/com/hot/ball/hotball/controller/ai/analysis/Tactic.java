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
                    for (Player occupant : toRate.getOccupants()) {
                        if (player.getTeam().getOpponent().isMember(occupant)) {
                            enemiesTZOn += occupant.getTackleZoneSize();
                        }
                    }

                    for (VoronoiArea neighbor : toRate.getConnected()) {
                        for (Player occupant : neighbor.getOccupants()) {
                            if (player.getTeam().getOpponent().isMember(occupant)) {
                                enemiesTZNext += occupant.getTackleZoneSize();
                            }
                        }
                    }

                    return Math.PI * (enemiesTZOn + 0.5 * enemiesTZNext);
                }
            };

    public abstract double rate(VoronoiArea toRate, Player player);

}
