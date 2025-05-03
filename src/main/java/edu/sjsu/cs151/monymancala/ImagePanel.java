package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

/**
 * ImagePanel is a custom JPanel that displays a background image scaled to fit
 * the size of the panel. It is used in the Welcome Frame of the Mancala game.
 */
public class ImagePanel extends JPanel {
    private Image welcomeImage;

    /**
     * Author: Brandon Sanchez
     * Constructs an ImagePanel with the specified image path.
     *
     * @param imagePath the path to the image file to be displayed
     */
    public ImagePanel(String imagePath) {
        // Load the image
        welcomeImage = new ImageIcon(imagePath).getImage();
    }

    /**
     * Author: Brandon Sanchez
     * Paints the image on the panel, scaling it to the current size of the panel.
     *
     * @param g the Graphics context used for painting
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the image scaled to the size of the panel
        g.drawImage(welcomeImage, 0, 0, getWidth(), getHeight(), this);
    }
}
