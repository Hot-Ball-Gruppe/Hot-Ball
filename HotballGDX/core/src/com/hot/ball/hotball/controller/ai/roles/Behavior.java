/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.controller.ai.roles;

import com.hot.ball.help.math.Vector;
import com.hot.ball.hotball.universe.player.Player;

/**
 *
 * @author Dromlius
 */
public interface Behavior {
    
    
    public Vector action(Player p);
}
