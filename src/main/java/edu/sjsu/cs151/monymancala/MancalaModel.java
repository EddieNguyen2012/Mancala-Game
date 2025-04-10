package edu.sjsu.cs151.monymancala;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MancalaModel {

    interface MancalaIIterator extends Iterator<Pit> {
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        boolean hasNext();

        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Override
        Pit next();
    }
    // private Player aPlayer;
    // private board board

    private List<Pit> pits;
    private List<ChangeListener> observers;

    private MancalaIIterator iterator;

    private int selectedIndex;

    private int currentPlayer;

    public MancalaModel(int stonesPerPit) {
        pits = new ArrayList<>();
        observers = new ArrayList<>();
        initializeBoard(stonesPerPit);
        selectedIndex = 0;

        // Initialize iterator
        iterator = new MancalaIIterator() {

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
         currentPlayer = 1;
    }

    private void initializeBoard(int stonesPerPit) {
        for (int i = 0; i < 14; i++) {
            if (i == 6 || i == 13)
                pits.add(new Pit("Mancala", i, 0));
            else
                pits.add(new Pit("Pit", i, stonesPerPit));
        }
    }

    public Pit getPit(int index) {
        return pits.get(index);
    }

    /*
    TODO: To be implemented at a later date
    */
    public void makeMove(int index) {
        if (isValidMove(index)) {
            distributeStones(index);
        }
    }

    private boolean isValidMove(int index) {
        Pit selected = this.getPit(index);

        // Check if the selected pit is selectable by the player (avoid player choosing different player's pit)
        if (selected.getPlayer() != this.currentPlayer)
            return false;

        // Check if the selected pit is a mancala
        if (selected.getIndex() % 7 == 0) {
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
            selected.addStone(1);
        }

        // Check capture
        selected = iterator.next();
        if (selected.getPlayer() == currentPlayer && selected.getStoneCount() == 0) {
            capture(index + moves);
        }
        else
            selected.addStone(1);
    }

    // if your last stone lands in an empty pit on your side of the board, you capture both that stone
    // and all stones in the opposite pit on your opponent's side, placing them in your own store
    private void capture(int selectedIndex) {
        int stones = pits.get(selectedIndex).getStoneCount() + 1;
        pits.get(selectedIndex).setStoneCount(0);
        if (selectedIndex < 7) {
            pits.get(6).addStone(stones);
        }
        else
            pits.get(13).addStone(stones);
    }

    public void undo() {
        return;
    }



    //Attaching listener to view
    public void addChangeListener(ChangeListener changeListener) {
        observers.add(changeListener);
    }

    //Notifying the view
    private void notifyView() {
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener observer : observers) {
            observer.stateChanged(event);
        }
    }

    public void printBoard() {

        System.out.print("Player 2 pit: ");
        for (int i = pits.size() - 1; i >= 7 ; i--) {
            System.out.print(pits.get(i).getStoneCount() + " ");
        }
        System.out.println();
        System.out.print("Player 1 pit: ");
        for (int i = 0; i < 7; i++) {
            System.out.print(pits.get(i).getStoneCount() + " ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        MancalaModel model = new MancalaModel(4);
        model.printBoard();

        model.makeMove(2);
        model.printBoard();

        model.makeMove(4);
        model.printBoard();

        model.makeMove(9);
        model.printBoard();
    }
}
