package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

// Custom JPanel to draw the background image
public class BackgroundPanel extends JPanel {
    private BoardStyle style;

    public BackgroundPanel(BoardStyle style) {
        this.style = style;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        style.drawBackground(g2, 0, 0, getWidth(), getHeight());
    }
}
