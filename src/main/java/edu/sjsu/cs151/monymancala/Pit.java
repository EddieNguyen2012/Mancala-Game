package edu.sjsu.cs151.monymancala;

public class Pit {
    private String pitLocation;
    private int index;
    private int stoneCount;

    private final int mancala; // 0 if not mancala, 1 if pit is mancala of player 1, 2 if pit is mancala of player 2

    public Pit (String pitLocation, int index, int stoneCount, int mancala) {
        this.pitLocation = pitLocation;
        this.index = index;
        this.stoneCount = stoneCount;
        this.mancala = mancala;
        this.selected = false;
    }

    //Getters
    String getPitLocation() {
        return pitLocation;
    }

    public int getIndex() {
        return index;
    }

    public int getMancala() {
        return mancala;
    }

    public int getStoneCount() {
        return stoneCount;
    }

    public boolean isEmpty() {
        return stoneCount == 0;
    }

    public int getPlayer() {
        if (this.index <= 6)
            return 1;
        else return 2;
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

    public void addStone(int stones) {
        this.stoneCount += stones;
    }

    /*
    public void removeStone() {
        this.stoneCount--;
    }
    */
    public Pit copy() {
        return new Pit(this.pitLocation, this.index, this.stoneCount, this.mancala);
    }
}
