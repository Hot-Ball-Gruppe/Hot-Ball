/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.help.math;

import java.io.Serializable;

/**
 *
 * @author Felix
 */
public class Vector implements Serializable {

    public final static Vector NULL_VECTOR = new Vector(0,0);

    private double dX;
    private double dY;

    public Vector(double dX, double dY) {
        this.dX = dX;
        this.dY = dY;
    }
    
    public Vector(double dX, double dY, double length) {
        this.dX = dX;
        this.dY = dY;
        setLength(length);
    }

    public Vector(Vector vector) {
        this.dX = vector.getdX();
        this.dY = vector.getdY();
    }

    public Vector(double length, double theta, Object nullObject) {
        this.dX = length * Math.cos(theta);
        this.dY = length * Math.sin(theta);
    }

    public Vector() {
    }

    public double getdX() {
        return dX;
    }

    public double getdY() {
        return dY;
    }

    public void setdX(double dX) {
        this.dX = dX;
    }

    public void setdY(double dY) {
        this.dY = dY;
    }

    public void addVector(Vector vector) {
        dX += vector.getdX();
        dY += vector.getdY();
    }

    public Vector multiply(double factor) {
        dX *= factor;
        dY *= factor;
        return this;
    }

    public double getLength() {
        return Math.sqrt(dX * dX + dY * dY);
    }

    public double getTheta() {
        return Math.atan2(dY, dX);
    }

    @Override
    public String toString() {
        return ("Vector : " + dX + "/" + dY);
    }

    public final Vector setLength(double length) {
        if (getLength() != 0) {
            multiply(length / getLength());
        }/* else {
            multiply(0);
        }*/
        return this;
    }
    
    public void flipdX(){
        setdX(-getdX());
    }
    public void flipdY(){
        setdY(-getdY());
    }
    
    public Vector rotateClockwise() {
        double oldX = getdX();
        double oldY = getdY();
        setdX(-oldY);
        setdY(oldX);
        return this;
    }

    public void rotate(double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double oldX = getdX();
        double oldY = getdY();
        setdX(cos*oldX-sin*oldY);
        setdY(sin*oldX+cos*oldY);
    }
}
