/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.analysis;

import com.hot.ball.help.math.Position;
import com.hot.ball.hotball.universe.player.Player;

/**
 *
 * @author Inga
 */
public class Node implements Comparable<Node> {

    private final VoronoiArea voronoiArea;
    private final Node predecessor;
    private final double rating;
    private final double heuristic;

    public Node(VoronoiArea voronoiArea, Node predecessor, Position target, Tactic[] tactics, Player player) {
        this.voronoiArea = voronoiArea;
        this.predecessor = predecessor;
        this.heuristic = (predecessor != null)?predecessor.getHeuristic():0 + heuristic(voronoiArea.getCenter(), target);
        double tmpRating = (predecessor != null)?predecessor.getRating():0;
        for (Tactic t : tactics) {
            tmpRating += t.rate(voronoiArea, player);
        }
        this.rating = tmpRating;
    }

    public double getRating() {
        return rating;
    }

    public double getHeuristic() {
        return heuristic;
    }

    @Override
    public int compareTo(Node t) {
        return (int) Math.signum((getHeuristic() + getRating()) - (t.getHeuristic() + t.getRating()));
    }

    private static double heuristic(Position a, Position b) {
        return a.getDistance(b);
    }

    public VoronoiArea getVoronoiArea() {
        return voronoiArea;
    }
    
    
}
