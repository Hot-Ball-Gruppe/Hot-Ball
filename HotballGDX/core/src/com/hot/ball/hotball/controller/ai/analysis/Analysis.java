/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.analysis;

import com.badlogic.gdx.Gdx;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Position.FinalPosition;
import com.hot.ball.hotball.controller.ai.util.Util;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.player.Player;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Dromlius
 */
public class Analysis {

    private static Analysis singleton;

    public static void create(String fileHandle) {
        if (singleton != null) {
            throw new RuntimeException("Analysis already created!");
        }
        singleton = new Analysis(fileHandle);
    }

    public static Analysis get() {
        if (singleton == null) {
            throw new RuntimeException("Analysis not yet created!");
        }
        return singleton;
    }

    private Map<Position.FinalPosition, VoronoiArea> voronoiAreas;

    private Analysis(String fileHandle) {
        voronoiAreas = new HashMap<>();
        Map<Position.FinalPosition, Set<Position.FinalPosition>> connections = new HashMap<>();

        BufferedReader isr = new BufferedReader(new InputStreamReader(Gdx.files.internal(fileHandle).read()));

        try {

            String[] centerPointsLine = isr.readLine().split("\\&");
            for (String centerpointString : centerPointsLine) {

                Position.FinalPosition center = positionFromString(centerpointString);
                String[] areaLine = isr.readLine().split("\\#");
                String[] statsLine = areaLine[0].split("\\$");
                String[] connectionLine = areaLine[1].split("\\&");

                VoronoiArea va = new VoronoiArea(center);

                va.setAtkRating(Integer.decode(statsLine[0]));
                va.setDefRating(Integer.decode(statsLine[1]));
                va.setMobRating(Integer.decode(statsLine[2]));

                connections.put(center, new HashSet<>());
                for (String connectionPoint : connectionLine) {
                    connections.get(va.getCenter()).add(positionFromString(connectionPoint));
                }
                voronoiAreas.put(center, va);
            }

            for (Position.FinalPosition center : connections.keySet()) {
                VoronoiArea v = voronoiAreas.get(center);
                for (Position.FinalPosition othercenter : connections.get(center)) {
                    v.addConnection(voronoiAreas.get(othercenter));
                }
            }
            isr.close();
        } catch (IOException ioe) {

        }
    }

    public void recalculate() {
        for (VoronoiArea va : voronoiAreas.values()) {
            va.reset();
        }
        for (GameObject go : GameObject.ALL_GAMEOBJECTS) {
            if (go instanceof Player) {
                Player p = (Player) go;
                FinalPosition closestCenter = null;
                double closestDist = Double.POSITIVE_INFINITY;
                for (FinalPosition centerPos : voronoiAreas.keySet()) {
                    double dist = centerPos.getDistance(p.getPosition());
                    if (dist < closestDist) {
                        closestDist = dist;
                        closestCenter = centerPos;
                    }
                }
                VoronoiArea va = voronoiAreas.get(closestCenter);
                va.addOccupant(p);
                p.setVoronoiArea(va);
            }
        }
    }

    private Position.FinalPosition positionFromString(String s) {
        String[] split = s.split("\\|");
        return new Position.FinalPosition(Integer.decode(split[0]), Integer.decode(split[1]));
    }

    public static enum Filter {
        canBePassedTo {
            @Override
            public boolean applies(Player p, VoronoiArea toTest) {
                return Util.canThrow(Ball.get().getBallCarrier(), toTest.getCenter());
            }
        }, noEnemies0 {
            @Override
            public boolean applies(Player p, VoronoiArea toTest) {
                for (Player occupant : toTest.getOccupants()) {
                    if (occupant.getTeam().equals(p.getTeam().getOpponent())) {
                        return false;
                    }
                }
                return true;
            }
        }, noEnemies1 {
            @Override
            public boolean applies(Player p, VoronoiArea toTest) {
                if (noEnemies0.applies(p, toTest)) {
                    for (VoronoiArea neighbor : toTest.getConnected()) {
                        if (!noEnemies0.applies(p, neighbor)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        }, AggroRatingBetterThanBC {
            @Override
            public boolean applies(Player p, VoronoiArea toTest) {
                Player ballCarrier = Ball.get().getBallCarrier();
                return ballCarrier.getVoronoiArea().getAttackRating(p.getTeam()) < toTest.getAttackRating(p.getTeam());
            }
        }, noAllys0 {
            @Override
            public boolean applies(Player p, VoronoiArea toTest) {
                for (Player occupant : toTest.getOccupants()) {
                    if (p.equals(occupant)) {
                        continue;
                    }
                    if (occupant.getTeam().equals(p.getTeam())) {
                        return false;
                    }
                }
                return true;
            }
        },noAllys1 {
            @Override
            public boolean applies(Player p, VoronoiArea toTest) {
                if (noAllys0.applies(p, toTest)) {
                    for (VoronoiArea neighbor : toTest.getConnected()) {
                        if (!noAllys0.applies(p, neighbor)) {
                            return false;
                        }
                    }
                }
                return true;
            }
        };

        public abstract boolean applies(Player p, VoronoiArea toTest);
    }

    public Set<VoronoiArea> processQuery(Filter[] query, Player p) {
        Set<VoronoiArea> currentSet = new HashSet<>(voronoiAreas.values());
        Set<VoronoiArea> backUpSet = new HashSet<>(currentSet);

        for (Filter f : query) {
            for (Iterator<VoronoiArea> iterator = currentSet.iterator(); iterator.hasNext();) {
                VoronoiArea next = iterator.next();
                if (!f.applies(p, next)) {
                    iterator.remove();
                }
            }
            if (currentSet.isEmpty()) {
                currentSet.addAll(backUpSet);
            } else {
                backUpSet.clear();
                backUpSet.addAll(currentSet);
            }
        }
        return currentSet;
    }
}
