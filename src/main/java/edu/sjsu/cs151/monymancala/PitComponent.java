package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

public class PitComponent extends JPanel {
    private MancalaModel model;
    private int pitIndex;
    private BoardStyle style;
    private static final Dimension PIT_SIZE = new Dimension(150, 200);

    /**
     * Constructs a PitComponent that visually represents a pit on the Mancala board.
     *
     * @param model the MancalaModel representing the current state of the game
     * @param pitIndex the index of the pit this component represents
     * @param style the visual style used to render the pit
     */
    public PitComponent(MancalaModel model, int pitIndex, BoardStyle style){
        this.pitIndex = pitIndex;
        this.model = model;
        this.style = style;
        setOpaque(false);
        setPreferredSize(PIT_SIZE);
    }

    /**
     * Author: Brandon Sanchez, Marco Lopez
     *
     * Returns the Pit object associated with this component.
     *
     * @return the corresponding Pit from the model
     */
    public Pit getCorrespondingPit() {
        return model.getPit(pitIndex);
    }

    /**
     * Author: Brandon Sanchez, Marco Lopez
     *
     * Paints the pit and its stones using the provided Graphics context.
     *
     * @param g the Graphics context to use for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        Pit pit = model.getPit(pitIndex);

        style.drawPit(g2, 0, 0, width, height);

        int stoneCount = pit.getStoneCount();

        int stonesPerRow = 4;
        int stoneSize = 15;

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
