package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

public class PitComponent extends JPanel {
    private final Pit pit;
    private BoardStyle style;
    private static final Dimension PIT_SIZE = new Dimension(60, 120);

    public PitComponent(Pit pit, BoardStyle style){
        this.pit = pit;
        this.style = style;
        setOpaque(false);
        setPreferredSize(PIT_SIZE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // Define pit shape/boundary
        Shape pitBoundary = style.getPitBoundary(width, height);

        style.drawPit(g2, 0, 0, width, height);

        int stoneCount = pit.getStoneCount();
        int padding = 10;
        int stonesPerRow = 4;

        int usableWidth = width - 2 * padding;
        int usableHeight = height - 2 * padding;

        // Sizes of stones determined based on space
        int stoneSize = Math.min(Math.min(usableWidth / stonesPerRow - 4, usableHeight / 3 - 4), 16);

        // grid dimensions
        int numRows = (int) Math.ceil((double) stoneCount / stonesPerRow);
        int gridWidth = stonesPerRow * (stoneSize + 4);
        int gridHeight = numRows * (stoneSize + 4);

        // centering grid in middle of pit
        int xOffset = (width - gridWidth) / 2;
        int yOffset = (height - gridHeight) / 2;

        // drawing the stones
        for (int i = 0; i < stoneCount; i++) {
            int col = i % stonesPerRow;
            int row = i / stonesPerRow;
            int sx = xOffset + col * (stoneSize + 4);
            int sy = yOffset + row * (stoneSize + 4);
            style.drawStone(g2, sx, sy, stoneSize);
        }
    }

}
