package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

// Updates the image in the Welcome Frame
public class ImagePanel extends JPanel {
    private Image welcomeImage;

    public ImagePanel(String imagePath) {
        // Load the image
        welcomeImage = new ImageIcon(imagePath).getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image scaled to the size of the panel
        g.drawImage(welcomeImage, 0, 0, getWidth(), getHeight(), this);
    }
}
