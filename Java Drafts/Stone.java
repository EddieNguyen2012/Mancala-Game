/**
 * Group Project: Mancala Game
 *
 *
 *
 */


package mancalagame;

/**
 *
 * This is the stone class that contains the stone characteristics and functions
 *  
 */
public class Stone {
    
    // Variables
    char row;
    int column;
    
    // Constructor
    Stone(char row, int column) {
        this.row = row;
        this.column = column;
    }
    
    
    // Getter Functions
    String getStoneLocation() {
        return Integer.toString(this.column) + this.row;
    }
    
}
