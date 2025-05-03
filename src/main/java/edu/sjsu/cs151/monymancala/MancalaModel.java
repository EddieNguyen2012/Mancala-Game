package edu.sjsu.cs151.monymancala;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

public class MancalaModel {

    private List<Pit> pits;
    private List<ChangeListener> observers;

    private MancalaIterator iterator;
    private BoardStyle style;

    private int selectedIndex;
    private List<Pit> lastPit;

    private int currentPlayer;

    /**
     * Author: Eddie Nguyen, Marco Lopez, Brandon Sanchez
     * Initializes a new MancalaModel instance with an empty board, observers, and default player.
     * Sets up the game board and initializes the iterator for traversing pits.
     */
    public MancalaModel() {

        pits = new ArrayList<>();
        observers = new ArrayList<>();
        style = new ModernStyle();
        initializeBoard();

        selectedIndex = 0;

        // Initialize iterator
        iterator = new MancalaIterator() {

            /**
             * Author: Eddie Nguyen
             *
             * Check if current index is not out of bound
             * @return true if next index (currentPit + 1) is out of bound
             */
            @Override
            public boolean hasNext() {
                return selectedIndex + 1 < pits.size();
            }

            /**
             * Author: Eddie Nguyen
             *
             * Return the next pit in the pits list. If the current pit is at the end of the list, set the current
             * pit to the beginning and return the first one.
             * @return
             */
            @Override
            public Pit next() {
                if (this.hasNext()) {
                    return pits.get(++selectedIndex);
                }
                else {
                    selectedIndex = 0;
                    return pits.get(selectedIndex);
                }
            }
        };
         currentPlayer = 0;

    }

    /**
     * Author: Eddie Nguyen, modified by Marco Lopez and Brandon Sanchez
     *
     * Initializes the game board with 14 pits (including two Mancalas).
     * Pits are added with default values, with two Mancalas for each player at positions 6 and 13.
     */
    private void initializeBoard() {
        for (int i = 0; i < 14; i++) {
            switch (i) {
                case 6: {
                    pits.add(new Pit("Mancala A", i, 0, 0));
                    break;
                }
                case 13: {
                    pits.add(new Pit("Mancala B", i, 0, 1));
                    break;
                }
                default:
                    pits.add(new Pit("Pit", i, 0, 2));
            }
        }
        backUp();
    }

    /**
     * Author: Eddie Nguyen, Brandon Sanchez
     *
     * Sets the number of stones in each non-Mancala pit to the specified number.
     * This method also triggers a backup and notifies the view of the changes.
     *
     * @param num the number of stones to set in each non-Mancala pit
     */
    public void setPitStones(int num) {
        for(Pit pit: pits) {
            if(pit.getIndex() != 6 && pit.getIndex() != 13) {
                pit.setStoneCount(num);
            }
        }
        backUp();
        notifyView();
    }

    /**
     * Author: Brandon Sanchez
     * Retrieves the pit at the specified index.
     *
     * @param index the index of the pit to retrieve
     * @return the Pit object at the specified index
     */
    public Pit getPit(int index) {
        return pits.get(index);
    }

    /**
     * Author: Eddie Nguyen
     *
     * Return current index
     *
     * @return current index
     */
    public int getSelectedIndex() {
        return selectedIndex;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Get current active player
     * @return current player label
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Change current active player
     * @param currentPlayer
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Creates a backup of the current state of all pits in the game.
     * This is used for undo functionality.
     */
    public void backUp() {
        lastPit = new ArrayList<>();
        for (Pit pit: pits) {
            lastPit.add(new Pit(pit));
        }
    }

    /**
     * Author: Eddie Nguyen, Brandon Sanchez
     *
     * @param index the index to make move
     * @return true if the move is valid, false otherwise
     */
    public boolean makeMove(int index) {
        if (isValidMove(index)) {
            backUp();
            distributeStones(index);
            notifyView();
            return true;
        }
        return false;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Checks if the move at the specified index is valid.
     * Validity is determined by the player’s turn, whether the pit is a Mancala, and whether the pit has stones.
     *
     * @param index the index of the pit to check
     * @return true if the move is valid, false otherwise
     */
    private boolean isValidMove(int index) {
        Pit selected = this.getPit(index);

        // Check if the selected pit is selectable by the player (avoid player choosing different player's pit)
        if (selected.getPlayer() != this.currentPlayer)
            return false;

        // Check if the selected pit is a mancala
        if (selected.getMancala() != 2) {
            return false;
        }

        // Check if the pit is empty
        if (selected.getStoneCount() == 0) {
            return false;
        }

        return true;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Distributes the stones from the selected pit to the other pits based on the rules of Mancala.
     * It handles the movement of stones, captures, and placing stones in the correct pits.
     *
     * @param index the index of the pit from which stones are distributed
     */
    private void distributeStones(int index) {
        selectedIndex = index;
        Pit selected = pits.get(selectedIndex);
        int moves = selected.getStoneCount();
        selected.setStoneCount(0);
        for (int i = 1; i < moves; i++) {
            selected = iterator.next();
            if (selected.getMancala() == 2 || selected.getMancala() == currentPlayer)
                selected.addStone(1);
            else{
                selected = iterator.next();
                selected.addStone(1);
            }

        }

        // Check capture
        selected = iterator.next();
        if (selected.getMancala() != 2 && selected.getMancala() != currentPlayer){
            selected = iterator.next();
        }
        if (selected.getPlayer() == currentPlayer && selected.getStoneCount() == 0 && selected.getMancala() == 2) {
            if (!capture(selected.getIndex())) {
                selected.addStone(1);
            }
        }
        else {
            selected.addStone(1);
        }
    }

    /**
     * Author: Eddie Nguyen
     *
     * Handles the capture of stones when the last stone lands in an empty pit on the player's side of the board.
     * The captured stones, along with the last stone, are moved to the player’s Mancala.
     *
     * @param selectedIndex the index of the pit where the capture occurs
     * @return true if the capture is successful, false otherwise
     */
    private boolean capture(int selectedIndex) {
        if (pits.get(selectedIndex).getMancala() != 2) {
            return false;
        }
        int stones = pits.get(12 - selectedIndex).getStoneCount() + 1;
        if (stones - 1 == 0) { // In case opposite pit is empty
            return false;
        }
        pits.get(12 - selectedIndex).setStoneCount(0);
        if (selectedIndex < 7) {
            pits.get(6).addStone(stones);
        }
        else
            pits.get(13).addStone(stones);
        return true;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Undoes the previous move by restoring the board to its previous state from the backup.
     * Also notifies the view of the changes.
     */
    public void undo() {
        pits = new ArrayList<>();
        for (Pit pit: lastPit) {
            pits.add(new Pit(pit));
        }
        backUp();
        notifyView();
    }

    /**
     * Author: Brandon Sanchez
     *
     * Adds a listener to observe changes to the game state.
     * This is used to notify the view when the game state changes.
     *
     * @param changeListener the ChangeListener to add
     */
    public void addChangeListener(ChangeListener changeListener) {
        observers.add(changeListener);
    }

    /**
     * Author: Brandon Sanchez
     *
     * Sets the style of the board.
     *
     * @param style the BoardStyle to apply
     */
    public void setBoardStyle(BoardStyle style){
        this.style = style;
    }

    /**
     * Author: Brandon Sanchez
     *
     * Retrieves the current style of the board.
     *
     * @return the current BoardStyle
     */
    public BoardStyle getStyle(){
        return style;
    }

    /**
     * Author: Brandon Sanchez
     *
     * Notifies all observers (view) about a change in the game state.
     * This triggers a state change event for the view to update.
     */
    private void notifyView() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener observer : observers) {
            observer.stateChanged(event);
        }
    }
}
