/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.test;

import com.hot.ball.help.math.Position;
import com.hot.ball.help.ui.old.DoubleBufferedPanel;
import com.hot.ball.hotball.controller.HumanController;
import com.hot.ball.hotball.controller.ai.AIController;
import com.hot.ball.hotball.logic.LogicCore;
import com.hot.ball.hotball.ui.KeyBinding;
import com.hot.ball.hotball.ui.UserInput;
import com.hot.ball.hotball.universe.GameObject;
import com.hot.ball.hotball.universe.ball.Ball;
import com.hot.ball.hotball.universe.collision.CollisionModell;
import com.hot.ball.hotball.universe.player.Player;
import com.hot.ball.hotball.universe.player.Team;
import com.hot.ball.hotball.universe.player.TeamColor;
import javax.swing.JFrame;

/**
 *
 * @author Dromlius
 */
public class TestFrame {

    public static void main(String[] args) {
        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        testFrame.setSize(1024, 768);
        CollisionModell.generate();
        DoubleBufferedPanelImpl doubleBufferedPanelImpl = new DoubleBufferedPanelImpl(1024, 768);
        doubleBufferedPanelImpl.setFocusable(true);
        UserInput.create(doubleBufferedPanelImpl, KeyBinding.WASD,UserInput.ControlMode.ScreenRelational);
        HumanController.create();
        Team redTeam = new Team(TeamColor.Red);
        Team blueTeam = new Team(TeamColor.Blue);
        Player ai2 = new Player(null, redTeam, new AIController(), new Position.DoublePosition(460, 500));
         Player player = new Player(null, blueTeam, HumanController.get(), new Position.DoublePosition(400, 400));
        Player ai = new Player(null, blueTeam, new AIController(), new Position.DoublePosition(300, 500));
       Ball.create(player);
        
        
        /*testFrame.addKeyListener( HumanController.get());
        testFrame.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                    // System.out.println("pause");
                    GameLoop.get().pause();
                }
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                    // System.out.println("resume");
                    GameLoop.get().resume();
                }
            }

        });*/
        testFrame.add(doubleBufferedPanelImpl);

        testFrame.setVisible(true);
        doubleBufferedPanelImpl.startDrawing();
        LogicCore.create();
        LogicCore.get().start();
    }

    private static class DoubleBufferedPanelImpl extends DoubleBufferedPanel {

        @SuppressWarnings("OverridableMethodCallInConstructor")
        public DoubleBufferedPanelImpl(int width, int height) {
            super(width, height);
          /*  addMouseListener(new MouseAdapter() {

                @Override
                public void mousePressed(MouseEvent e) {
                    if (Ball.get().getState() instanceof Controlled) {
                        Player carrier = ((Controlled)Ball.get().getState()).getBallCarrier();
                        Ball.get().setState(new InAir(carrier, new Vector(1000, Ball.get().getPosition().angleBetween(e.getPoint()), null)));
                    }
                }

            });*/
        }

        @Override
        protected void internalDraw() {
            for(GameObject go:GameObject.ALL_GAMEOBJECTS){
                go.draw(graphics);
            }
        }
    }
}
