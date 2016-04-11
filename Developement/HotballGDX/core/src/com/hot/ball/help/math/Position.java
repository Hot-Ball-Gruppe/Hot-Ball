/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.help.math;

import java.awt.Point;
import java.awt.geom.Point2D;

/**
 *
 * @author Dromlius
 */
public abstract class Position {

    public abstract double getX();

    public abstract double getY();

    public int getRoundX() {
        return (int) Math.round(getX());
    }

    public int getRoundY() {
        return (int) Math.round(getY());
    }

    public double getDistance(Position to) {
        return Math.sqrt((to.getX() - getX()) * (to.getX() - getX()) + (to.getY() - getY()) * (to.getY() - getY()));
    }

 /*   public double getDistance2(Position to) {
        return (to.getX() - getX()) * (to.getX() - getX()) + (to.getY() - getY()) * (to.getY() - getY());
    }

    public double getDistance2(double x1, double y1) {
        return (x1 - getX()) * (x1 - getX()) + (y1 - getY()) * (y1 - getY());
    }*/

    public double angleBetween(Position to) {
        return Math.atan2(to.getY() - getY(), to.getX() - getX());
    }
    
    public double angleBetween(Point to) {
        return Math.atan2(to.getY() - getY(), to.getX() - getX());
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Position)) {
            return false;
        }

        return ((getX() == ((Position) obj).getX()) && (getY() == ((Position) obj).getY()));
    }

    public boolean equals(Position pos) {
        return ((getX() == pos.getX()) && (getY() == pos.getY()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (int) (Double.doubleToLongBits(getX()) ^ (Double.doubleToLongBits(getX()) >>> 32));
        hash = 71 * hash + (int) (Double.doubleToLongBits(getY()) ^ (Double.doubleToLongBits(getY()) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return "(" + getX() + " | " + getY() + ")";
    }

    public boolean isIntegerPos() {
        return (getX() % 1 == 0) && (getY() % 1 == 0);
    }

    public static class DoublePosition extends Position {

        private double x;
        private double y;

        public DoublePosition(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public DoublePosition(Point point) {
            this.x = point.getX();
            this.y = point.getY();
            Point2D.Double d = new Point2D.Double();
        }

        public DoublePosition(Position position) {
            this.x = position.getX();
            this.y = position.getY();
        }

        @Override
        public double getX() {
            return x;
        }

        @Override
        public double getY() {
            return y;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }

        public void addVector(Vector vector) {
            setX(getX() + vector.getdX());
            setY(getY() + vector.getdY());
        }

        public void addVector(Vector vector, double factor) {
            setX(getX() + vector.getdX()*factor);
            setY(getY() + vector.getdY()*factor);
        }
        
        public void addVector(double dx, double dy) {
            setX(getX() + dx);
            setY(getY() + dy);
        }

        public void set(DoublePosition position) {
            setX(position.getX());
            setY(position.getY());
        }
        
         public void set(Point position) {
            setX(position.getX());
            setY(position.getY());
        }
    }

    public static class FinalPosition extends Position {

        private final double x;
        private final double y;

        public FinalPosition(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public FinalPosition(Point point) {
            this.x = point.getX();
            this.y = point.getY();
        }

        public FinalPosition(Position position) {
            this.x = position.getX();
            this.y = position.getY();
        }

        @Override
        public double getX() {
            return x;
        }

        @Override
        public double getY() {
            return y;
        }
    }
}
