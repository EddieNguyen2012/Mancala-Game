package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

public class MancalaComponent extends JComponent {
    private final Pit pit;
    private BoardStyle style;
    private static final Dimension MANCALA_SIZE = new Dimension(80, 160);

    public MancalaComponent(Pit pit, BoardStyle style){
        this.pit = pit;
        this.style = style;
        setPreferredSize(MANCALA_SIZE);
        setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        style.drawMancala(g2, 0, 0, width, height);

        // Draw stones
        int stoneCount = pit.getStoneCount();
        int padding = 10;
        int stonesPerColumn = 6;
        int usableWidth = width - 2 * padding;
        int usableHeight = height - 2 * padding;

        // stones size based on space
        int stoneSize = Math.min(Math.min(usableWidth / 3 - 4, usableHeight / stonesPerColumn - 4), 16);

        // grid dimension
        int numColumns = (int) Math.ceil((double) stoneCount / stonesPerColumn);
        int gridWidth = numColumns * (stoneSize + 4);
        int gridHeight = stonesPerColumn * (stoneSize + 4);

        // centering the grid
        int xOffset = (width - gridWidth) / 2;
        int yOffset = (height - gridHeight) / 2;

        for (int i = 0; i < stoneCount; i++) {
            int col = i / stonesPerColumn;
            int row = i % stonesPerColumn;
            int sx = xOffset + col * (stoneSize + 4);
            int sy = yOffset + row * (stoneSize + 4);
            style.drawStone(g2, sx, sy, stoneSize);;
        }
    }

}
