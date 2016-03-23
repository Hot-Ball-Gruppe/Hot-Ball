/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.controller.ai.analysis.Analysis;
import com.hot.ball.hotball.controller.ai.analysis.Node;
import com.hot.ball.hotball.controller.ai.analysis.Tactic;
import com.hot.ball.hotball.controller.ai.analysis.VoronoiArea;
import com.hot.ball.hotball.universe.player.Player;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author Dromlius
 */
public abstract class Behavior {

    public final static Behavior PIRANHA = new Piranha();
    public final static Behavior DISRUPTOR = new Disruptor();
    public final static Behavior MARKING = new Marking();
    public final static Behavior FORWARD = new Forward();
    public final static Behavior ZONEDEFENSE = new ZoneDefense();
    public final static Behavior CENTER = new Center();
    public final static Behavior STUPID = new Stupid();
    public final static Behavior DEAD = new Dead();

    protected Behavior() {
    }

    public abstract Vector action(Player p);

    protected Vector goToPlayer(Player p, Player target) {
        if (p.getPosition().getDistance(target.getPosition()) < 2 * p.getTackleZoneSize() / 3) {

        }
        return new Vector(1, p.getPosition().angleBetween(target.getPosition()), null);
    }
    
    protected Position tacticalMovement(Player p,Position target,Tactic[] tactics){
        PriorityQueue<Node> openNodes = new PriorityQueue<>();
        Set<VoronoiArea> visitedNodes = new HashSet<>();
        
        if(p.getPosition().getDistance(target)<10){
            return target;
        }
        
        Node startNode = new Node(Analysis.get().getVoronoi(target), null, p.getPosition(), tactics, p);
        openNodes.add(startNode);
        visitedNodes.add(p.getVoronoiArea());
        
        while(!openNodes.isEmpty()){
            Node currentNode = openNodes.poll();
            for(VoronoiArea neighbor:currentNode.getVoronoiArea().getConnected()){
                if(p.getVoronoiArea().equals(neighbor)){
                    return currentNode.getVoronoiArea().getCenter();
                }
                if(visitedNodes.add(neighbor)){
                    openNodes.add(new Node(neighbor, currentNode, p.getPosition(), tactics, p));
                }
            }
        }
        System.out.println("PATHFIND-FAIL!");
        return target;
    }
    
    protected Vector goTo(Player p, Position target) {
        if(p.getPosition().getDistance(target)<10){
            return Vector.NULL_VECTOR;
        }
        return new Vector(target.getX() - p.getPosition().getX(), target.getY() - p.getPosition().getY()).setLength(Math.min(1, p.getPosition().getDistance(target)));
    }
}
