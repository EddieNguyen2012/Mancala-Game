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


    /**
     * Constructs a new MancalaController with the specified model.
     * Initializes turn and move tracking variables.
     *
     * @param model the MancalaModel representing the game logic
     */
    public MancalaController(MancalaModel model) {
        this.model = model;
        canEndMove = false;
        moveCounter = 0;
    }

    /**
     * Author: Eddie Nquyen, Marco Lopez, Brandon Sanchez, Danny Nguyen
     * Sets the view for the controller and initializes button and pit interactions.
     * Attaches mouse listeners to the "End Turn" and "Undo" buttons and sets up the pit components.
     *
     * @param view the MancalaView instance to associate with this controller
     */
    public void setView(MancalaView view) {
        this.view = view;

        //Style buttons
        JButton defaultStyleButton = view.getDefaultStyleButton();
        JButton modernStyleButton = view.getModernStyleButton();
        JButton oceanStyleButton = view.getOceanStyleButton();

        defaultStyleButton.addActionListener(e -> setBoardStyle(new DefaultStyle()));
        modernStyleButton.addActionListener(e -> setBoardStyle(new ModernStyle()));
        oceanStyleButton.addActionListener(e -> setBoardStyle(new OceanStyle()));


        //Stone count buttons
        JButton stones3button = view.getStones3Button();
        JButton stones4button = view.getStones4Button();

        stones3button.addActionListener(e -> setPitStones(3));
        stones4button.addActionListener(e -> setPitStones(4));
        if(view.getEndTurnButton() != null) {
            this.view.getEndTurnButton().addMouseListener(new MouseAdapter() {
                /**
                 * Author: Eddie Nguyen
                 * <p>
                 * Handles mouse click events on the "End Turn" button.
                 * If ending the turn is allowed (canEndMove is true),
                 * it triggers the endTurn() logic which switches the player
                 * or grants an additional turn if appropriate.
                 *
                 * @param e the MouseEvent representing the click on the button
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (canEndMove) {
                        endTurn();
                    }
                }
            });

            this.view.getUndoButton().addMouseListener(new MouseAdapter() {
                /**
                 * Author: Eddie Nguyen
                 * <p>
                 * Handles mouse click events on the "Undo" button.
                 * If ending the turn is allowed (canEndMove is true),
                 * it triggers an undo action as long as the undo limit has not been exceeded.
                 *
                 * @param e the MouseEvent representing the click on the button
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (canEndMove) {
                        undo();
                    }
                }
            });
        }

        pits = view.getPitComponents();
        initPits();
    }

    /**
     * Author: Eddie Nguyen
     *
     * Initializes all pit components by attaching mouse listeners to each.
     * These listeners handle user interactions such as clicking a pit to make a move.
     */
    public void initPits() {
        for (PitComponent pit : pits) {
            pit.addMouseListener(getMouseListener(pit));
        }
    }

    /**
     * Author: Eddie Nguyen
     *
     * Ends the current player's turn.
     * If the last stone landed in the player's own mancala, they get another turn.
     * Otherwise, the turn switches to the other player. Resets the undo and move counters.
     * Also checks if the game is over before switching turns.
     */
    public void endTurn() {
        checkGameOver();
        if (model.getPit(model.getSelectedIndex()).getMancala() != model.getCurrentPlayer()) {
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

    /**
     * Author: Eddie Nguyen
     *
     * Undoes the last move if the undo limit (3 times per turn) has not been reached.
     * Updates the view and internal controller state. Shows an error message if the undo limit is exceeded.
     */
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
            /**
             * Author: Eddie Nguyen recycled Marco's template
             *
             * Handles mouse click events on a pit component. When a pit is clicked,
             * it selects the pit in the view and initiates a move if no move has been made yet
             * in the current turn. Updates the controller state accordingly.
             *
             * @param e the MouseEvent representing the mouse click
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                if(pitComponent.getCorrespondingPit().getPlayer() != model.getCurrentPlayer()) {
                    return;
                }
                view.highlightPit(pitComponent);
                if (moveCounter == 0) {
                    if(model.makeMove(pitComponent.getCorrespondingPit().getIndex())) {
                        canEndMove = true;
                        moveCounter++;
                    }
                }
            }

            /**
             * Author: Eddie Nguyen recycled Marco's template
             *
             * Set border color when Pit is clicked
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {
                if(pitComponent.getCorrespondingPit().getPlayer() == model.getCurrentPlayer()) {
                    pitComponent.setBorder(BorderFactory.createLineBorder(Color.CYAN, 2));
                }
            }

            /**
             * Author: Eddie Nguyen recycled Marco's template
             *
             * Hide border when mouse exited
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {
                pitComponent.setBorder(null);
            }
        };
    }
    /**
     * Author: Danny Nguyen
     *
     * Checks if the game is over by evaluating the pits of both players.
     * If the game is over, it calls the method to determine and display the winner.
     */
    public void checkGameOver() {
    	if (gameOver()) {
    		getWinner();
    	}
    }

    /**
     * Author: Danny Nguyen, Eddie Nguyen
     *
     * Determines if the game is over by checking if one side of the board is empty.
     *
     * @return true if either player's pits are all empty, false otherwise
     */
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
    /**
     * Author: Danny Nguyen, Eddie Nguyen
     *
     * Calculates the final scores by adding up the stones in each player's pits and mancala.
     * Determines the winner and displays the result using the view.
     */
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

    // Button Actions
    /**
     * Author: Marco Lopez, Brandon Sanchez
     *
     * Handles the action when a board style is selected. Applies the style and initializes the board.
     *
     * @param aStyle the selected board style
     */
    private void setBoardStyle(BoardStyle aStyle) {
        this.model.setBoardStyle(aStyle);
        view.closeWelcomeWindow();
        view.buildComponents();
        view.setVisible(true);
        view.showInitialCountFrame();
    }

    /**
     * Author: Marco Lopez, Brandon Sanchez
     *
     * Handles the action when a stone count is selected. Applies the number and closes the setup window.
     *
     * @param num number of stones per pit
     */
    private void setPitStones(int num) {
        this.model.setPitStones(num);
        view.closeInitialCountWindow();
    }
}