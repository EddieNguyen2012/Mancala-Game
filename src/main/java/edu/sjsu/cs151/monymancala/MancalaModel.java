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

    
    public MancalaModel() {
        
        pits = new ArrayList<>();
        observers = new ArrayList<>();
        style = new ModernStyle();
        initializeBoard();

        selectedIndex = 0;

        // Initialize iterator
        iterator = new MancalaIterator() {

            /**
             * Check if current index is not out of bound
             * @return true if next index (currentPit + 1) is out of bound
             */
            @Override
            public boolean hasNext() {
                return selectedIndex + 1 < pits.size();
            }

            /**
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

    public void setPitStones(int num) {
        for(Pit pit: pits) {
            if(pit.getIndex() != 6 && pit.getIndex() != 13) {
                pit.setStoneCount(num);
            }
        }
        backUp();
        notifyView();
    }

    public Pit getPit(int index) {
        return pits.get(index);
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void backUp() {
        lastPit = new ArrayList<>();
        for (Pit pit: pits) {
            lastPit.add(new Pit(pit));
        }
    }

    public boolean makeMove(int index) {
        if (isValidMove(index)) {
            backUp();
            distributeStones(index);
            notifyView();
            return true;
        }
        return false;
    }

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

    // if your last stone lands in an empty pit on your side of the board, you capture both that stone
    // and all stones in the opposite pit on your opponent's side, placing them in your own store
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

    public void undo() {
        pits = new ArrayList<>();
        for (Pit pit: lastPit) {
            pits.add(new Pit(pit));
        }
        backUp();
        notifyView();
    }

    //Attaching listener to view
    public void addChangeListener(ChangeListener changeListener) {
        observers.add(changeListener);
    }

    public void setBoardStyle(BoardStyle style){
        this.style = style;
    }

    public BoardStyle getStyle(){
        return style;
    }

    //Notifying the view
    private void notifyView() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener observer : observers) {
            observer.stateChanged(event);
        }
    }
}
