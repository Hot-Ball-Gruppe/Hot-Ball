/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotball.ui;

import java.awt.event.KeyEvent;

/**
 *
 * @author Dromlius
 */
public class KeyBinding {
    public static final KeyBinding WASD = new KeyBinding(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);
    public static final KeyBinding ARROWS = new KeyBinding(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
    
    private final int up,down,left,right;

    public KeyBinding(int up, int down, int left, int right) {
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
    
}
