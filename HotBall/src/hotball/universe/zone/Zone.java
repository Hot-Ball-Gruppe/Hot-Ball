/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotball.universe.zone;

import hotball.universe.GameObject;
import java.util.Collection;

/**
 *
 * @author Dromlius
 */
public interface Zone {
    public static final Collection<Zone> ALL_ZONES;
    
    public boolean contains(GameObject go);
}
