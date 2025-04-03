package edu.sjsu.cs151.monymancala;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

public class MancalaModel {

    // private Player aPlayer;
    // private board board
    
    
    private List<Pit> pits;
    private List<ChangeListener> observers;

    public MancalaModel(int stonesPerPit) {
        pits = new ArrayList<>();
        observers = new ArrayList<>();
        initializeBoard(stonesPerPit);
    }

    private void initializeBoard(int stonesPerPit) {

    }

    public Pit getPit(int index) {
        return pits.get(index);
    }

    /*
    TODO: To be implemented at a later date

    public void makeMove() {

    }

    private boolean isValidMove() {

    }

    private boolean distributeStones() {

    }

    private void checkCaptureRule() {

    }

    public void undo() {

    }
     */


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
}
