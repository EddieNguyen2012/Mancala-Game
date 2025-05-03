package edu.sjsu.cs151.monymancala;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class OceanStyle implements BoardStyle{
    
    private Image board;
    private Image mancala;
    private Image pit;
    private Image stones;
    private Color labelColor;

    /**
     * Author: Danny Nguyen
     *
     * Constructs an OceanStyle board style, loading the ocean-themed images for
     * the board, pits, mancala store, and stones. Sets the label color to white.
     */
    public OceanStyle() {
        board = new ImageIcon("Images/OceanBoard.png").getImage();
        pit = new ImageIcon("Images/OceanPit.png").getImage();
        stones = new ImageIcon("Images/SandDollar.png").getImage();
        mancala = new ImageIcon("Images/BeachMancala.png").getImage();
        this.labelColor = Color.WHITE;
    }

    /**
     * Author: Danny Nguyen
     *
     * Draws a pit using the ocean-themed pit image.
     *
     * @param g2 the Graphics2D context used for rendering
     * @param x the x-coordinate of the top-left corner of the pit
     * @param y the y-coordinate of the top-left corner of the pit
     * @param width the width of the pit
     * @param height the height of the pit
     */
    @Override
    public void drawPit(Graphics2D g2, int x, int y, int width, int height) {
        g2.drawImage(pit, x, y, width, height, null);
    }

    /**
     * Author: Danny Nguyen
     *
     * Draws the Mancala store using the ocean-themed mancala image.
     *
     * @param g2 the Graphics2D context used for rendering
     * @param x the x-coordinate of the top-left corner of the Mancala store
     * @param y the y-coordinate of the top-left corner of the Mancala store
     * @param width the width of the Mancala store
     * @param height the height of the Mancala store
     */
    @Override
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height) {
        g2.drawImage(mancala, x, y, width, height, null);
    }

    /**
     * Author: Danny Nguyen
     *
     * Draws a sand dollar stone using the ocean-themed stone image.
     *
     * @param g2 the Graphics2D context used for rendering
     * @param x the x-coordinate of the top-left corner of the stone
     * @param y the y-coordinate of the top-left corner of the stone
     * @param size the diameter of the stone
     */
    @Override
    public void drawStone(Graphics2D g2, int x, int y, int size) {
        g2.drawImage(stones, x, y, size, size, null);
    }

    /**
     * Author: Danny Nguyen
     *
     * Draws the ocean-themed game board background.
     *
     * @param g2 the Graphics2D context used for rendering
     * @param x the x-coordinate of the top-left corner of the background
     * @param y the y-coordinate of the top-left corner of the background
     * @param width the width of the background
     * @param height the height of the background
     */
    @Override
    public void drawBackground(Graphics2D g2, int x, int y, int width, int height) {
        g2.drawImage(board, x, y, width, height, null);
    }

    /**
     * Author: Danny Nguyen
     *
     * Returns the color used for text labels in this board style.
     *
     * @return the Color for labels, which is white
     */
    @Override
    public Color getLabelColor() {
        return labelColor;
    }

}