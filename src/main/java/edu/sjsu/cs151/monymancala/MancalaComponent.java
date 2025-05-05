package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;

/**
 * MancalaComponent is a custom Swing component that represents a single Mancala pit.
 * It visually displays the pit using a specified BoardStyle and renders the stones
 * within the pit based on the current game model.
 */
public class MancalaComponent extends JComponent {
    //private final Pit pit;
    private MancalaModel model;
    private int pitIndex;
    private BoardStyle style;
    private static final Dimension MANCALA_SIZE = new Dimension(150, 400);

    /**
     * Author: Brandon Sanchez, Marco Lopez
     * Constructs a MancalaComponent with the given model, pit index, and board style.
     *
     * @param model the game model containing pit information
     * @param pitIndex the index of the pit this component represents
     * @param style the BoardStyle used to render the component
     */
    public MancalaComponent(MancalaModel model, int pitIndex, BoardStyle style){
        //this.pit = pit;
        this.model = model;
        this.pitIndex = pitIndex;
        this.style = style;
        setPreferredSize(MANCALA_SIZE);
        setOpaque(false);
    }

    /**
     * Author: Brandon Sanchez, Marco Lopez
     * Paints the Mancala pit and its stones using the specified board style.
     *
     * @param g the Graphics context used for painting
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        Pit pit = model.getPit(pitIndex);

        style.drawMancala(g2, 0, 0, width, height);

        // Draw stones
        int stoneCount = pit.getStoneCount();

        int stonesPerColumn = 6;

        // stones size based on space
        int stoneSize = 15;

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
