/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.logic;

/**
 *
 * @author Dromlius
 */
public interface BlockCondition {
    public boolean isBlocking();
    public boolean isPermanent();
    public void end();
}
