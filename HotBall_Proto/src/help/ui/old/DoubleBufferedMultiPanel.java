/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help.ui.old;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;

/**
 *
 * @author Dromlius
 */
public abstract class DoubleBufferedMultiPanel extends JPanel {

    protected boolean drawing;
    private List<Region> offScreenRegions;
    private Map<Integer, Graphics> regionGraphics;
    private final Thread drawThread;
    private final List<RegionSpace> regionSpacing;

    public DoubleBufferedMultiPanel(int width, int height, List<RegionSpace> regionSpacing) {
        setSize(width, height);
        this.regionSpacing = regionSpacing;
        this.drawThread = new Thread(new Runnable() {
            @Override
            @SuppressWarnings("SleepWhileInLoop")
            public void run() {
                while (drawing) {
                    try {
                        internalDraw();
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void startDrawing() {
        drawing = true;
        drawThread.start();
    }

    public void endDrawing() {
        drawing = false;
    }

    @Override
    public final void setSize(int width, int height) {
        super.setSize(width, height); //To change body of generated methods, choose Tools | Templates.
        offScreenRegions = null;
    }

    private void internalDraw() {
        if (offScreenRegions == null) {
            offScreenRegions = new ArrayList<>();
            regionGraphics = new HashMap<>();
            for (RegionSpace rs : regionSpacing) {
                Region r = new Region((int) (rs.getX() * getWidth()), (int) (rs.getY() * getHeight()), (int) (rs.getWidth() * getWidth()), (int) (rs.getHeight() * getHeight()), rs.getID());
                offScreenRegions.add(r);
                regionGraphics.put(r.getID(), r.getImage().createGraphics());
            }
        }
        for (Region region : offScreenRegions) {
            int id = region.getID();
            Graphics g = regionGraphics.get(id);
            g.clearRect(0, 0, region.getWidth(), region.getHeigth());
            draw(region.getWidth(), region.getHeigth(),g, id);
            try {
                getGraphics().drawImage(region.getImage(), region.getX(), region.getY(), null);
            } catch (NullPointerException npe) {
                System.out.println("No graphics up yet!");
            }
        }
    }

    protected abstract void draw(int width, int heigth, Graphics g, int regionID);

    private static class Region {

        private final BufferedImage image;
        private final int x, y;
        private final int id;
        private final int width;
        private final int heigth;

        public Region(int x, int y, int width, int heigth, int ID) {
            this.image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
            this.x = x;
            this.y = y;
            this.id = ID;
            this.width = width;
            this.heigth = heigth;
        }

        /**
         * @return the image
         */
        public BufferedImage getImage() {
            return image;
        }

        /**
         * @return the x
         */
        public int getX() {
            return x;
        }

        /**
         * @return the y
         */
        public int getY() {
            return y;
        }

        /**
         * @return the id
         */
        public int getID() {
            return id;
        }

        public int getWidth() {
            return width;
        }

        public int getHeigth() {
            return heigth;
        }

    }

    public static class RegionSpace {

        private final int ID;
        private final double x, y;
        private final double width, height;

        public RegionSpace(int ID, double x, double y, double width, double height) {
            this.ID = ID;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        /**
         * @return the ID
         */
        public int getID() {
            return ID;
        }

        /**
         * @return the x
         */
        public double getX() {
            return x;
        }

        /**
         * @return the y
         */
        public double getY() {
            return y;
        }

        /**
         * @return the width
         */
        public double getWidth() {
            return width;
        }

        /**
         * @return the height
         */
        public double getHeight() {
            return height;
        }

    }
}
