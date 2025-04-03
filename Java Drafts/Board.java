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

public class Board {
    
    // Variables
    private ArrayList<Pit> board; // The board will contain an array of pits
    
    
    
    // Constructor
    Board() {
        this.board = new ArrayList<>();
    }
    
    
    // Functions
    
    // Returns the pit object that matches the correct location
    public Pit getPit(String location){
        
        for(Pit aPit : board){
            if (aPit.getPitLocation() == location)
                return aPit;
        }
        
        return null;
    }
}
