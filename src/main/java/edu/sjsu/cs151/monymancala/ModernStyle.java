package edu.sjsu.cs151.monymancala;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import javax.swing.*;

public class ModernStyle implements BoardStyle{
    
    private Image board;
    private Image mancala;
    private Image pit;
    private Image stones;
    private Color labelColor;

    /**
     * Author: Marco Lopez
     *
     * Constructing the BoardStyle object
     */
    public ModernStyle() {
        board = new ImageIcon("Images/MancalaBoard.png").getImage();
        pit = new ImageIcon("Images/PitDesign.png").getImage();
        stones = new ImageIcon("Images/RedBall.png").getImage();
        mancala = new ImageIcon("Images/Mancala.png").getImage();
        this.labelColor = Color.WHITE;
    }

    /**
     * Author: Marco Lopez
     *
     * Draws a pit on the board using the specified graphics context and dimensions.
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
     * Author: Marco Lopez
     *
     * Draws the Mancala store on the board using the specified graphics context and dimensions.
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
     * Author: Marco Lopez
     *
     * Draws a stone at the specified location with the given size.
     *
     * @param g2 the Graphics2D context used for rendering
     * @param x the x-coordinate of the top-left corner of the stone
     * @param y the y-coordinate of the top-left corner of the stone
     * @param size the size (diameter) of the stone
     */
    @Override
    public void drawStone(Graphics2D g2, int x, int y, int size) {
        g2.drawImage(stones, x, y, size, size, null);
    }

    /**
     * Author: Marco Lopez
     *
     * Draws the game board background using the specified graphics context and dimensions.
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
     * Author: Marco Lopez
     *
     * Returns the color used for labels in this board style.
     *
     * @return the Color used for labels
     */
    @Override
    public Color getLabelColor() {
        return labelColor;
    }

}
