package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MancalaController {

    private MancalaModel model;
    private MancalaView view;
    private ArrayList<PitComponent> pits;
    private int undoCount;

    private int currentPlayer;


    public MancalaController(MancalaModel model) {
        this.model = model;

        // Get buttons and link up here
    }

    public void setView(MancalaView view) {
        this.view = view;
        pits = view.getPitComponents();
        initPits();
    }

    public void initPits() {
        for (PitComponent pit : pits) {
            pit.addMouseListener(getMouseListener(pit));
        }
    }
    public void endTurn() {
        undoCount = 0;
        model.setCurrentPlayer((model.getCurrentPlayer() + 1) % 2);
        System.out.println(model.getCurrentPlayer());// Switch player indication
    }

    public void undo() {
        if (undoCount < 3) {
            model.undo();
            undoCount++;
        }
        else {
            view.showErrorMessage("Maximum 3 undo per turn");
        }
    }

    public MouseAdapter getMouseListener(PitComponent pitComponent) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                view.selectPit(pitComponent);
                if(model.makeMove(pitComponent.getCorrespondingPit().getIndex())) { // Check if move is valid
                    if (model.getPit(model.getSelectedIndex()).getMancala() != model.getCurrentPlayer()) {
                        endTurn();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                pitComponent.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                pitComponent.setBorder(null);
            }
        };
    }

    public void gameOver() {
        // Danny fill here;
        return;
    }
}
