/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.help.ui.old;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Felix Wiener
 */
public abstract class DoubleBufferedPanel extends JPanel {
    protected int x,y;
    
    protected boolean drawing = true;
    private BufferedImage offScreenImg;
    protected Graphics2D graphics;
    private static boolean normalMode = true;
    private final Thread drawThread;

    @SuppressWarnings({"LeakingThisInConstructor", "Convert2Lambda"})
    public DoubleBufferedPanel(int width, int height) {
        super.setSize(width, height);
        //        
        offScreenImg = new BufferedImage(super.getWidth(), super.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
        graphics = offScreenImg.createGraphics();
        //
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_F12) {
              //      screenshot = true;
                }
            }
        });
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
        }
       );
        //
    }
    
    public void startDrawing(){       
        drawThread.start();
    }
    
    protected abstract void internalDraw();

    private boolean screenshot = false;
    private static final SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyyG_MM_dd_H-m-s");

    @SuppressWarnings({"SleepWhileHoldingLock", "SleepWhileInLoop", "CallToThreadDumpStack"})

    public void draw() {
        if (offScreenImg == null) {
            try {
                offScreenImg = new BufferedImage(super.getWidth(), super.getHeight(), BufferedImage.TYPE_INT_ARGB_PRE);
                graphics = offScreenImg.createGraphics();
            } catch (java.lang.IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        graphics.clearRect(0, 0, offScreenImg.getWidth(), offScreenImg.getHeight());
        if (!normalMode) {
            graphics.setColor(new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255)));
            graphics.fillRect(0, 0, offScreenImg.getWidth(), offScreenImg.getHeight());
        }

        internalDraw();

        try {
           getGraphics().drawImage(offScreenImg, x, y, this);
           
           
           
           
           
           
         //  getGraphics().drawImage(offScreenImg.getScaledInstance((int)(offScreenImg.getWidth()*3.2), (int)(offScreenImg.getHeight()*3.2), BufferedImage.SCALE_FAST), 0, 0, null);
        } catch (NullPointerException nPE) {
            //      System.out.println("No Graphics up yet...");
        }

        if (screenshot) {
            try {
                ImageIO.write(offScreenImg, "png", new File("screen@" + new StringBuilder(dateformatYYYYMMDD.format(new Date())).toString().replaceAll(" ", "") + ".png"));
                System.out.println("Screenshot taken [" + "screen@" + new StringBuilder(dateformatYYYYMMDD.format(new Date())).toString().replaceAll(" ", "") + "]");
            } catch (IOException ioE) {
                System.out.println("Cant screenshot!!!!");
                ioE.printStackTrace();
            }
            screenshot = false;
        }
    }

    protected void endDrawing() {
        drawing = false;
    }

    public static boolean isNormalMode() {
        return normalMode;
    }

    public static void setDiscoMode() {
        normalMode = false;
    }

    public synchronized BufferedImage getOffScreenImg() {
        return offScreenImg;
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        offScreenImg = null;
    }

}
