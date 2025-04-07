/**
 * Group Project: Mancala Game
 *
 *
 *
 */

package mancalagame;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This is the Model class.
 *  
 */
public class MancalaModel {
    private Player aPlayer;
    private Player bPlayer;
    private Board board;
    
    private List<Pit> pits;
    private List<ChangeListener> observers;
    
    // Constructor
    public MancalaModel(Player aPlayer, Player bPlayer, int stonesPerPit) {
        pits = new ArrayList<>();
        observers = new ArrayList<>();
        initializeBoard(stonesPerPit);
        
        this.aPlayer = aPlayer;
        this.bPlayer = bPlayer;
    }

    private void initializeBoard( int stonesPerPit) {
    }

    public Pit getPit(int index) {
        return pits.get(index);
    }
    
    public void moveNextPit(Player player, Stone stone) {
    
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
