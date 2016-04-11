/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotball.controller;

import help.math.Vector;
import hotball.ui.UserInput;
import hotball.universe.player.Player;

/**
 *
 * @author Dromlius
 */
public class HumanController implements Controller {

    private static HumanController singleton;

    public static void create() {
        if (singleton != null) {
            throw new RuntimeException("Humancontroller already created!");
        }
        singleton = new HumanController();
    }

    public static HumanController get() {
        if (singleton == null) {
            throw new RuntimeException("Humancontroller not yet created!");
        }
        return singleton;
    }
    
    private HumanController(){}

    @Override
    public double getFacing(Player player) {
        return player.getPosition().angleBetween(UserInput.get().getMousePosition());
    }
    
    @Override
    public Vector getMoveVector(Player player) {
        /*Vector movement = new Vector(UserInput.get().getKeyX(), UserInput.get().getKeyY());
        .getMovementVector().setLength(1);*/
        return UserInput.get().getMovementVector();
    }
}
