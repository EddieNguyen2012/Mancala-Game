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
    private boolean canEndMove;
    private int moveCounter;


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
                if (canEndMove) {
                    undo();
                }
            }
        });

    }

    public void initPits() {
        for (PitComponent pit : pits) {
            pit.addMouseListener(getMouseListener(pit));
        }
    }

    public void endTurn() {
        if (model.getPit(model.getSelectedIndex()).getMancala() != model.getCurrentPlayer()) {
            checkGameOver();
            undoCount = 0;
            moveCounter = 0;
            canEndMove = false;
            model.setCurrentPlayer((model.getCurrentPlayer() + 1) % 2);
            view.getPlayerText().setText("Player: " + (int) (model.getCurrentPlayer() + 1));
        }
        else {
            view.getPlayerText().setText("Additional turn for Player " + (int) (model.getCurrentPlayer() + 1));
            moveCounter = 0;
            undoCount = 0;
            canEndMove = false;
        }


    }

    public void undo() {
        if (undoCount < 3) {
            model.undo();
            view.getPitPanel().repaint();
            undoCount++;
            moveCounter = 0;
            canEndMove = false;
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
                        canEndMove = true;
                        moveCounter++;
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
    public void checkGameOver() {
    	if (gameOver()) {
    		getWinner();
    	}
    }
    

    public boolean gameOver() {
        boolean playerAstats = true;
        for (int i = 0; i < 6; i++) {
            if (model.getPit(i).getStoneCount() > 0) {
                playerAstats = false;
                break;
            }
        }
        boolean playerBstats = true;
        for (int i = 7; i < 13; i++) {
            if (model.getPit(i).getStoneCount() > 0) {
                playerBstats = false;
                break;
            }
        }
        return playerAstats || playerBstats;
    }
    private void getWinner() {
        int playerAMancala = model.getPit(6).getStoneCount();
        int playerAStones = 0;
        int playerBMancala = model.getPit(13).getStoneCount();
        int playerBStones = 0;

        for (int i = 0; i < 6; i++) {
            playerAStones += model.getPit(i).getStoneCount();
        }

        for (int i = 7; i < 13; i++) {
            playerBStones += model.getPit(i).getStoneCount();
        }

        int playerATotal = playerAMancala + playerAStones;
        int playerBTotal = playerBMancala + playerBStones;
        String winner;

        if (playerATotal > playerBTotal) {
            winner = "Player A Wins!";
        } else if (playerATotal < playerBTotal) {
            winner = "Player B Wins!";
        } else {
            winner = "It's a Tie!";
        }
        //calls on view to display winner message.
        view.showGameOverMessage("Game Over! and the results are " + winner);
    }
}