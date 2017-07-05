package edu.vt.ece.laice.grapher.gui.splash;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by cameronearle on 7/5/17.
 */
public class ImagePanel extends JPanel {

    BufferedImage img;

    public ImagePanel() {
        setOpaque(false);
        try {
            img = ImageIO.read((ClassLoader.getSystemResource("LDA_Splash.png")));
        } catch (IOException e) {
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(829, 506);
    }
}