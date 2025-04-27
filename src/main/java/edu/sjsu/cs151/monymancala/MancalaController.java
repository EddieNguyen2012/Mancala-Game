package edu.sjsu.cs151.monymancala;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.EventListener;

public class MancalaController {

    private MancalaModel model;
    private MancalaView view;
    private ArrayList<PitComponent> pits;
    private int undoCount;
    private boolean canEndMove;
    private int moveCounter;
    private int currentPlayer;


    public MancalaController(MancalaModel model) {
        this.model = model;
        canEndMove = false;
        moveCounter = 0;
    }

    public void setView(MancalaView view) {
        this.view = view;
        pits = view.getPitComponents();
        initPits();
        this.view.getEndTurnButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (canEndMove) {
                    endTurn();
                }
            }
        });
        this.view.getUndoButton().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                undo();
            }
        });

    }

    public void initPits() {
        for (PitComponent pit : pits) {
            pit.addMouseListener(getMouseListener(pit));
        }
    }
    public void endTurn() {
        undoCount = 0;
        moveCounter = 0;
        canEndMove = false;
        model.setCurrentPlayer((model.getCurrentPlayer() + 1) % 2);
        System.out.println(model.getCurrentPlayer());// Switch player indication
        view.getPlayerText().setText("Player: " + (int) (model.getCurrentPlayer() + 1));

    }

    public void undo() {
        if (undoCount < 3) {
            model.undo();
            view.getPitPanel().repaint();
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
                if (moveCounter == 0) {
                    if(model.makeMove(pitComponent.getCorrespondingPit().getIndex())) {
                        if (model.getPit(model.getSelectedIndex()).getMancala() != model.getCurrentPlayer()) {
                            canEndMove = true;
                            moveCounter++;
                        }
                        else {
                            view.getPlayerText().setText("Additional turn for Player " + (int) (model.getCurrentPlayer() + 1));
                            moveCounter = 0;
                        }
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
