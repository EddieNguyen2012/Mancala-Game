package edu.sjsu.cs151.monymancala;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class DefaultStyle implements BoardStyle {
    private final Color stoneColor;
    private final Color pitOutlineColor;
    private final Color pitColor;
    private final Color mancalaOutlineColor;
    private final Color backgroundColor;
    private final Color labelColor;

    public DefaultStyle() {
        this.stoneColor = Color.BLACK;
        this.pitColor = Color.WHITE;
        this.pitOutlineColor = Color.BLACK;
        this.mancalaOutlineColor = Color.BLACK;
        this.backgroundColor = Color.GRAY;
        this.labelColor = Color.WHITE;
    }

    @Override
    public void drawPit(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(pitColor);
        g2.fillOval(x,y,width,height);
        g2.setColor(pitOutlineColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x,y,width,height);
    }

    @Override
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(pitColor);
        g2.fillOval(x,y,width,height);
        g2.setColor(mancalaOutlineColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x,y,width,height);
    }

    @Override
    public void drawStone(Graphics2D g2, int x, int y, int size) {
        g2.setColor(stoneColor);
        g2.fillOval(x,y,size,size);
    }

    @Override
    public void drawBackground(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(backgroundColor);
        g2.fillRect(x,y,width,height);
    }

    @Override
    public Color getLabelColor() {
        return labelColor;
    }


}
