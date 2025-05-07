package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

// Custom JPanel to draw the background image
public class BackgroundPanel extends JPanel {
    private BoardStyle style;

    /**
     * Author: Marco Lopez
     * Constructs a BackgroundPanel with the specified BoardStyle.
     *
     * @param style the BoardStyle used to draw the background
     */
    public BackgroundPanel(BoardStyle style) {
        this.style = style;
        setOpaque(false);
    }

    /**
     * Author: Marco Lopez
     * Overrides the paintComponent method to draw the custom background
     * using the BoardStyle's drawBackground method.
     *
     * @param g the Graphics object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        style.drawBackground(g2, 0, 0, getWidth(), getHeight());
    }
}
