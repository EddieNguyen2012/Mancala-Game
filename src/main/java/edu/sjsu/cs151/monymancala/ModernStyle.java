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

    public ModernStyle() {
        board = new ImageIcon("Images/MancalaBoard.png").getImage();
        pit = new ImageIcon("Images/PitDesign.png").getImage();
        stones = new ImageIcon("Images/RedBall.png").getImage();
        mancala = new ImageIcon("Images/Mancala.png").getImage();
        this.labelColor = Color.WHITE;
    }

    @Override
    public void drawPit(Graphics2D g2, int x, int y, int width, int height) {
        g2.drawImage(pit, x, y, width, height, null);
    }

    @Override
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height) {
        g2.drawImage(mancala, x, y, width, height, null);
    }

    @Override
    public void drawStone(Graphics2D g2, int x, int y, int size) {
        g2.drawImage(stones, x, y, size, size, null);
    }

    @Override
    public void drawBackground(Graphics2D g2, int x, int y, int width, int height) {
        g2.drawImage(board, x, y, width, height, null);
    }

    @Override
    public Color getLabelColor() {
        return labelColor;
    }

}
