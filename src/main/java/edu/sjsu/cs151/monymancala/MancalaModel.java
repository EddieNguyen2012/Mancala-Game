package edu.sjsu.cs151.monymancala;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.*;

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
    private List<Pit> lastPit;

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
            switch (i) {
                case 6: {
                    pits.add(new Pit("Mancala A", i, 0, 1));
                    break;
                }
                case 13: {
                    pits.add(new Pit("Mancala B", i, 0, 2));
                    break;
                }
                default:
                    pits.add(new Pit("Pit", i, stonesPerPit, 0));
            }
        }
        backUp();
    }

    public Pit getPit(int index) {
        return pits.get(index);
    }

    public void backUp() {
        lastPit = new ArrayList<>();
        for (Pit pit: pits) {
            lastPit.add(pit.copy());
        }
    }

    /*
    TODO: To be implemented at a later date
    */
    public void makeMove(int index) {
        if (isValidMove(index)) {
            backUp();
            distributeStones(index);
        }
    }

    private boolean isValidMove(int index) {
        Pit selected = this.getPit(index);

        // Check if the selected pit is selectable by the player (avoid player choosing different player's pit)
        if (selected.getPlayer() != this.currentPlayer)
            return false;

        // Check if the selected pit is a mancala
        if (selected.getMancala() != 0) {
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
            if (selected.getMancala() == 0 || selected.getMancala() == currentPlayer)
                selected.addStone(1);
            else{
                selected = iterator.next();
                selected.addStone(1);
            }

        }

        // Check capture
        selected = iterator.next();
        if (selected.getMancala() != 0 && selected.getMancala() != currentPlayer){
            selected = iterator.next();
        }
        if (selected.getPlayer() == currentPlayer && selected.getStoneCount() == 0) {
            capture(selected.getIndex());
        }
        else {
            selected.addStone(1);
        }
    }

    // if your last stone lands in an empty pit on your side of the board, you capture both that stone
    // and all stones in the opposite pit on your opponent's side, placing them in your own store
    private void capture(int selectedIndex) {
        if (pits.get(selectedIndex).getMancala() != 0) {
            return;
        }
        int stones = pits.get(12 - selectedIndex).getStoneCount() + 1;
        pits.get(12 - selectedIndex).setStoneCount(0);
        if (selectedIndex < 7) {
            pits.get(6).addStone(stones);
        }
        else
            pits.get(13).addStone(stones);
    }

    public void undo() {
        pits = new ArrayList<>(lastPit);
        backUp();
//        notifyView();
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
        System.out.print("(last pit)\nPlayer 2 pit: ");
        for (int i = lastPit.size() - 1; i >= 7 ; i--) {
            System.out.print(lastPit.get(i).getStoneCount() + " ");
        }
        System.out.println();
        System.out.print("Player 1 pit: ");
        for (int i = 0; i < 7; i++) {
            System.out.print(lastPit.get(i).getStoneCount() + " ");
        }
        System.out.println();

        System.out.print("(current)\nPlayer 2 pit: ");
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
        Scanner sc = new Scanner(System.in);
        int i = 1;
        System.out.print("Enter new move: ");
        int index;
        while (sc.hasNextInt()) {
            index = sc.nextInt();
            System.out.println("\nMove " + i + " selected index: " + index);
            if (index == 20){
                model.undo();
                model.printBoard();
            }
            else {
                model.makeMove(index);
                model.printBoard();

            }
            System.out.print("Enter new move: ");
        }

    }
}
