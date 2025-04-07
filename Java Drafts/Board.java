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
 * Board: 
 * Player A: ArrayList[0-6] Pits
 * Player B: ArrayList[7-13] Pits
 * 0 is the store for A
 * 13 is the store for B
 *  
 */

public class Board {
    
    // Variables
    private ArrayList<Pit> board; // The board will contain an array of pits
    private ArrayList<Stone> stones;
    
    // Constructor
    Board() {
        this.board = new ArrayList<>();
        this.stones = new ArrayList<>();
    }
    
    
    // Functions
    private void initializeBoard() {
        
        
        ArrayList<Stone> stones3 = new ArrayList<> ();                               // ArrayList that holds the 3 initial stones for each pit
        Pit aPit = new Pit();
        String location = "";
        
        // Adding the 36 stones to the 13 pits pn the board [1-13] because [0, 14] are the stores 
        for(int i = 1; i < 13; i++) {
            
            
            // The first row of pits [1-6]
            if(i < 6) {
                
                // Adding three instances of stones to the array list
                for(int j = 0; j < 3; j++) {
                    stones3.add(new Stone('a', i));
                }
                
                // Getting location of the pit
                location = Integer.toString(i) + 'a';
                
                // Adding the stones to a pit
                aPit.addInitialStones(stones3, false, location);
                
                // Adding the pit to the board
                board.add(aPit);
            }
            
            // The first row of pits [7-13]
            else {
                
                // Adding three instances of stones to the array list
                for(int j = 0; j < 3; j++) {
                    stones3.add(new Stone('b', i));
                }
                
                // Getting location of the pit
                location = Integer.toString(i) + 'b';
                
                // Adding the stones to a pit
                aPit.addInitialStones(stones3, false, location);
                
                // Adding the pit to the board
                board.add(aPit);
            }
        }
    }
    
    public void setPit(String location){
        
    }
    
    
    // Returns the pit object that matches the correct location
    public Pit getPit(String location){
        
        for(Pit aPit : board){
            if (aPit.getPitLocation() == location)
                return aPit;
        }
        
        return null;
    }
}
