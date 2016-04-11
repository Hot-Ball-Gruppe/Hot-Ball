/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dromlius
 */
public abstract class ScreenRegion extends Rectangle {

    private BufferedImage offScreenImg;
    protected Graphics2D offScreengraphics;

    private final List<ActionArea> actionAreas;

    public ScreenRegion(int x, int y, int width, int heigth) {
        super(x, y, width, heigth);
        actionAreas = new ArrayList<>();
    }

    public void externalDraw(Graphics drawOn) {
        if (offScreenImg == null) {
            offScreenImg = new BufferedImage((int) getWidth(), (int) getHeight(), BufferedImage.TYPE_INT_RGB);
            offScreengraphics = offScreenImg.createGraphics();
        }
        offScreengraphics.clearRect(0, 0, (int) getWidth(), (int) getHeight());
        draw(offScreengraphics);
        drawOn.drawImage(offScreenImg, (int) getX(), (int) getY(), null);
    }

    public final void mousePressed(MouseEvent me) {
        for (ActionArea aa : actionAreas) {
            if (aa.contains(me.getPoint())) {
                aa.action();
            }
        }
        internalMousePressed(me);
    }

    public abstract void mouseMoved(MouseEvent me);

    protected abstract void internalMousePressed(MouseEvent me);

    protected abstract void draw(Graphics2D offScreengraphics);

    protected static abstract class ActionArea extends Rectangle {

        public ActionArea(int x, int y, int width, int heigth) {
            super(x, y, width, heigth);
        }

        public abstract void action();
    }
}
