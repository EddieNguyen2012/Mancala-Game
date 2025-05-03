package edu.sjsu.cs151.monymancala;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

/**
 * DefaultStyle is an implementation of BoardStyle that provides a simple, neutral
 * visual appearance for the Mancala game board, including pits, mancalas, stones,
 * background, and labels.
 */
public class DefaultStyle implements BoardStyle {
    private final Color stoneColor;
    private final Color pitOutlineColor;
    private final Color pitColor;
    private final Color mancalaOutlineColor;
    private final Color backgroundColor;
    private final Color labelColor;

    /**
     * Author: Brandon Sanchez
     * Constructs a DefaultStyle instance with predefined colors for all board components.
     */
    public DefaultStyle() {
        this.stoneColor = Color.BLACK;
        this.pitColor = Color.WHITE;
        this.pitOutlineColor = Color.BLACK;
        this.mancalaOutlineColor = Color.BLACK;
        this.backgroundColor = Color.GRAY;
        this.labelColor = Color.WHITE;
    }

    /**
     * Author: Brandon Sanchez
     * Draws a pit as a filled oval with an outline.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the width of the pit
     * @param height the height of the pit
     */
    @Override
    public void drawPit(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(pitColor);
        g2.fillOval(x,y,width,height);
        g2.setColor(pitOutlineColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x,y,width,height);
    }

    /**
     * Author: Brandon Sanchez
     * Draws a Mancala as a filled oval with an outline.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the width of the Mancala
     * @param height the height of the Mancala
     */
    @Override
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(pitColor);
        g2.fillOval(x,y,width,height);
        g2.setColor(mancalaOutlineColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x,y,width,height);
    }

    /**
     * Author: Brandon Sanchez
     * Draws a stone as a filled oval.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the top-left corner of the stone
     * @param y the y-coordinate of the top-left corner of the stone
     * @param size the diameter of the stone
     */
    @Override
    public void drawStone(Graphics2D g2, int x, int y, int size) {
        g2.setColor(stoneColor);
        g2.fillOval(x,y,size,size);
    }

    /**
     * Author: Brandon Sanchez
     * Draws the background of the board as a filled rectangle.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the width of the background
     * @param height the height of the background
     */
    @Override
    public void drawBackground(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(backgroundColor);
        g2.fillRect(x,y,width,height);
    }

    /**
     * Author: Brandon Sanchez
     * Returns the color used for labels.
     *
     * @return the label color
     */
    @Override
    public Color getLabelColor() {
        return labelColor;
    }


}
