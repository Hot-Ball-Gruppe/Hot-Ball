/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hot.ball.help.util;

import com.badlogic.gdx.Gdx;
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Dromlius
 */
public class SpriteTurner {

    private final static int texture_size = 64;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            JFileChooser fd = new JFileChooser("X:\\Dromlius\\Dropbox\\java\\NetBeansProjects\\ActivProjects\\hotballGit\\Hot-Ball\\rawResources");
            JFrame preview = new JFrame();
            preview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            fd.showOpenDialog(preview);
            BufferedImage source = ImageIO.read(fd.getSelectedFile());
            BufferedImage target = new BufferedImage(source.getHeight(), source.getWidth(), source.getType());
            Graphics2D g = target.createGraphics();
            for (int x = 0; x < source.getWidth() / texture_size; x++) {
                for (int y = 0; y < source.getHeight() / texture_size; y++) {
                    g.drawImage(source.getSubimage(x * texture_size, y * texture_size, texture_size, texture_size), y * texture_size, x * texture_size, null);
                }
            }
            preview.setSize(target.getWidth(), target.getHeight());
            preview.setVisible(true);
            preview.getGraphics().drawImage(target, 0, 0, null);
            fd.setCurrentDirectory(new File("X:\\Dromlius\\Dropbox\\java\\NetBeansProjects\\ActivProjects\\hotballGit\\Hot-Ball\\HotballGDX\\core\\assets\\res"));
            if (fd.showSaveDialog(preview) == JFileChooser.APPROVE_OPTION) {
                ImageIO.write(target, "png", fd.getSelectedFile());
                System.out.println("saved");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("done!");
        System.exit(0);
    }

}
