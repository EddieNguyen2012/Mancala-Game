package edu.sjsu.cs151.monymancala;

public class Pit {
    private String pitLocation;
    private int index;
    private int stoneCount;
    private boolean isMancala;

    public Pit (String pitLocation, int index, int stoneCount, boolean isMancala) {
        this.pitLocation = pitLocation;
        this.index = index;
        this.stoneCount = stoneCount;
        this.isMancala = isMancala;
    }

    //Getters
    String getPitLocation() {
        return pitLocation;
    }

    public int getIndex() {
        return index;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public boolean isMancala() {
        return isMancala;
    }

    public boolean isEmpty() {
        return stoneCount == 0;
    }

    /*
    // Checks if the pit is full
    public boolean isFull() {
        
        // Returns false if the pit has 4 or more stones
        if (stoneCount) >= 4)
            return false;
        
        // Returns trues if pit has less than 4 stones
        return true;
    }
    */

    //setters
    public void setStoneCount(int stoneCount) {
        this.stoneCount = stoneCount;
    }

    public void addStone() {
        this.stoneCount++;
    }

    /*
    public void removeStone() {
        this.stoneCount--;
    }
    */
    
}
