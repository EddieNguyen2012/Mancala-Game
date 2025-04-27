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

public void checkGameOver() {
    	if (gameOver()) {
    		getWinner();
    	}
    }
    

    public boolean gameOver() {
        // Danny fill here;
        int[] board = model.getBoard();
        boolean playerAstats = true;
        for (int i = 0; i < 6; i++) {
            if (board[i] > 0) {
                playerAstats = false;
                break;
            }
        }
        boolean playerBstats = true;
        for (int i = 7; i < 13; i++) {
            if (board[i] > 0) {
                playerBstats = false;
                break;
            }
        }
        return playerAstats || playerBstats;
    }
    private void getWinner() {
        int[] board = model.getBoard(); 
        int playerAMancala = board[6];
        int playerAStones = 0;
        int playerBMancala = board[13];
        int playerBStones = 0;

        for (int i = 0; i < 6; i++) {
            playerAStones += board[i];
        }

        for (int i = 7; i < 13; i++) {
            playerBStones += board[i];
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
        JOptionPane.showMessageDialog(null, "Game Over! and the results are " + winner);
        view.gameOverMessage(winner);
    }
}
