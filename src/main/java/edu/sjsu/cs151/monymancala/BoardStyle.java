package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;


/**
 * BoardStyle defines the drawing behavior for the Mancala game's visual components,
 * such as pits, mancalas, stones, and background. Implementations of this interface
 * provide different visual styles for the game.
 */
public interface BoardStyle {
    /**
     * Author: Brandon Sanchez
     * Draws a pit at the specified location and dimensions.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the width of the pit
     * @param height the height of the pit
     */
    public void drawPit(Graphics2D g2, int x, int y, int width, int height);

    /**
     * Author: Brandon Sanchez
     * Draws a Mancala at the specified location and dimensions.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the width of the Mancala
     * @param height the height of the Mancala
     */
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height);

    /**
     * Author: Brandon Sanchez
     * Draws a stone at the specified location with a given size.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the stone
     * @param y the y-coordinate of the stone
     * @param size the diameter of the stone
     */
    public void drawStone(Graphics2D g2, int x, int y, int size);

    /**
     * Author: Brandon Sanchez
     * Draws the background of the game board.
     *
     * @param g2 the Graphics2D context
     * @param x the x-coordinate of the top-left corner
     * @param y the y-coordinate of the top-left corner
     * @param width the width of the background area
     * @param height the height of the background area
     */
    public void drawBackground(Graphics2D g2, int x, int y, int width, int height);

    /**
     * Author: Brandon Sanchez
     * Returns the color used for labels in the game.
     *
     * @return the Color used for text labels
     */
    public Color getLabelColor();
}
