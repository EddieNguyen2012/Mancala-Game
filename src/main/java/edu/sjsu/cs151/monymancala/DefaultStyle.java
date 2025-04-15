package edu.sjsu.cs151.monymancala;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

public class DefaultStyle implements BoardStyle{
    private final Color stoneColor;
    private final Color pitOutlineColor;
    private final Color mancalaOutlineColor;
    private final Color backgroundColor;

    public DefaultStyle() {
        this.stoneColor = Color.black;
        this.pitOutlineColor = Color.black;
        this.mancalaOutlineColor = Color.black;
        this.backgroundColor = Color.white;
    }

    @Override
    public void drawPit(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(pitOutlineColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x,y,width,height);
    }

    @Override
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height) {
        g2.setColor(pitOutlineColor);
        g2.setStroke(new BasicStroke(3));
        g2.drawOval(x,y,width,height);
    }

    @Override
    public void drawStone(Graphics2D g2, int x, int y, int size) {
        g2.setColor(stoneColor);
        g2.fillOval(x,y,size,size);
    }

    @Override
    public Shape getPitBoundary(int width, int height) {
        return new Ellipse2D.Double(0, 0, width, height);
    }

}
