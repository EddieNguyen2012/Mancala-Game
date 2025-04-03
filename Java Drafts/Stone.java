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
    String location;
    char row;
    int column;
    
    // Constructor
    Stone(char row, int column) {
        this.row = row;
        this.column = column;
        this.location = Integer.toString(column) + row;
    }
    
    
    // Getter Functions
    String getStoneLocation() {
        return Integer.toString(this.column) + this.row;
    }
    
}
