package edu.sjsu.cs151.monymancala;

import java.awt.*;

public interface BoardStyle {
    public void drawPit(Graphics2D g2, int x, int y, int width, int height);
    public void drawMancala(Graphics2D g2, int x, int y, int width, int height);
    public void drawStone(Graphics2D g2, int x, int y, int size);
    public Shape getPitBoundary(int width, int height);

}
