/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help.ui;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Dromlius
 */
public class ScreenPanel extends JPanel {

    protected boolean drawing = true;
    private final Thread drawThread;

    private final List<ScreenRegion> screenRegions;

    public ScreenPanel() {
        this.screenRegions = null;
        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent me) {
                for (ScreenRegion sr : screenRegions) {
                    if (sr.contains(me.getPoint())) {
                        me.translatePoint((int) -sr.getX(), (int) -sr.getY());
                        sr.mousePressed(me);
                        return;
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent me) {
                for (ScreenRegion sr : screenRegions) {
                    if (sr.contains(me.getPoint())) {
                        me.translatePoint((int) -sr.getX(), (int) -sr.getY());
                        sr.mouseMoved(me);
                        return;
                    }
                }
            }
        };
        drawThread = new Thread(new Runnable() {

            @Override
            @SuppressWarnings({"BroadCatchBlock", "TooBroadCatch", "SleepWhileInLoop"})
            public void run() {
                while (drawing) {
                    try {
                        draw();
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    private void draw() {
        Graphics g = getGraphics();
        for (ScreenRegion sr : screenRegions) {
            sr.externalDraw(g);
        }
    }

    public void startDrawing() {
        drawThread.start();
    }

    public void addScreenReagion(ScreenRegion sr) {
        screenRegions.add(sr);
    }

}
