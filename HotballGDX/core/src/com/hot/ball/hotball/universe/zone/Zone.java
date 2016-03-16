/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.hotball.universe.zone;

import com.hot.ball.hotball.universe.GameObject;
import java.util.Collection;
import java.util.Stack;

/**
 *
 * @author Dromlius
 */
public interface Zone {
    public static final Collection<Zone> ALL_ZONES = new Stack<>();
    
    public boolean contains(GameObject go);
}
