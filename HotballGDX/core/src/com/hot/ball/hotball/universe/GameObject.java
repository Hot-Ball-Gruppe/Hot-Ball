/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe;

import com.hot.ball.help.math.Position;
import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.ui.Graphics;
import com.hot.ball.hotball.universe.collision.CollisionModell;
import com.hot.ball.hotball.universe.zone.Zone;
import java.awt.Graphics2D;
import java.util.Collection;
import java.util.Stack;

/**
 *
 * @author Dromlius
 */
public abstract class GameObject {

    public static final Collection<GameObject> ALL_GAMEOBJECTS;

    static {
        ALL_GAMEOBJECTS = new Stack<>();
    }

    private static int idCounter;

    private final int id;

    private final Position.DoublePosition position;
    private final double size;

    @SuppressWarnings("LeakingThisInConstructor")
    protected GameObject(Position.DoublePosition startingPos, double size) {
        this.interferingZones = new Stack<>();
        id = idCounter++;
        position = startingPos;
        this.size = size;
        ALL_GAMEOBJECTS.add(this);
    }

    public int getId() {
        return id;
    }

    private final Stack<Zone> interferingZones;

    public void addZone(Zone z) {
        interferingZones.add(z);
    }

    public Stack<Zone> getInterferingZones() {
        return interferingZones;
    }

    protected void clearInterfeeringZones() {
        interferingZones.clear();
    }

    public abstract void action(double timeDiff);

    public abstract void draw(Graphics g);
    
    public Position.DoublePosition getPosition() {
        return position;
    }

    public double getSize() {
        return size;
    }

    private Vector currentVelocity = new Vector();
    private final double DECAY_BASE = 30;

    public Vector getCurrentVelocity() {
        return currentVelocity;
    }

    public void setCurrentVelocity(Vector currentVelocity) {
        this.currentVelocity = currentVelocity;
    }

    protected abstract double getDECAY_FACTOR();

    public void accelerate(double timeDiff, Vector accDir) {
        double decayRate = Math.pow(DECAY_BASE, getDECAY_FACTOR() * (-timeDiff));
        accDir.multiply(getCurrentMaxSpeed());
        currentVelocity.setdX(decayRate * (currentVelocity.getdX() - accDir.getdX()) + accDir.getdX());
        currentVelocity.setdY(decayRate * (currentVelocity.getdY() - accDir.getdY()) + accDir.getdY());
        CollisionModell.get().checkCollision(this, timeDiff);
        getPosition().addVector(currentVelocity, timeDiff);
    }

    public abstract double getCurrentMaxSpeed();

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GameObject other = (GameObject) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
