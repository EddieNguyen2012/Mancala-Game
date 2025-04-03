/**
 * Group Project: Mancala Game
 *
 *
 *
 */


package mancalagame;

import java.util.*;

/**
 *
 * This is the Pit class. Contains some functions which includes the Stone Class.
 *  
 */
public class Pit {
    
    // Variables
    private ArrayList<Stone> pit;       // Each pit will contain an array of stones with max count of 4
    private boolean store;              // The store is the players base
    private String location;            // Will consist of char and int. Example: A1, B4
    
    // Constructors
    Pit() {
        this.pit = new ArrayList<>();
        this.store = false;
        this.location = "";
    }
    
    Pit(ArrayList<Stone> pit, boolean store) {
        this.pit = pit;
        this.store = store;
    }
    
    
    // Functions
    
    public void addStone(Stone aStone) {
        this.pit.add(aStone);
    }
    
    public int getPitCount() {
        return this.pit.size();
    }
    
    public String getPitLocation() {
        return this.location;
    }
    
    // Checks if the pit is the player's store
    public boolean isStore() {
        return store;
    }
    
    // Checks if the pit is full
    public boolean isFull() {
        
        // Returns false if the pit has 4 or more stones
        if (this.pit.size() >= 4)
            return false;
        
        // Returns trues if pit has less than 4 stones
        return true;
    }
    
    
    
}
