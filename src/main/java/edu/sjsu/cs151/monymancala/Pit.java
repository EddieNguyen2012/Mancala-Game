package edu.sjsu.cs151.monymancala;

public class Pit {
    private String pitLocation;
    private int index;
    private int stoneCount;

    private final int mancala; // 0 if not mancala, 1 if pit is mancala of player 1, 2 if pit is mancala of player 2

    /**
     * Author: Brandon Sanchez, Eddie Nguyen
     *
     * Constructs a Pit with specified location, index, stone count, and mancala designation.
     *
     * @param pitLocation the identifier of the pit's location on the board
     * @param index the index of the pit
     * @param stoneCount the number of stones in the pit
     * @param mancala indicates if this pit is a mancala: 0 = no, 1 = player 1's mancala, 2 = player 2's mancala
     */
    public Pit (String pitLocation, int index, int stoneCount, int mancala) {
        this.pitLocation = pitLocation;
        this.index = index;
        this.stoneCount = stoneCount;
        this.mancala = mancala;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Constructs a new Pit by copying another Pit.
     *
     * @param pit the Pit object to copy
     */
    public Pit(Pit pit) {
        this.pitLocation = pit.pitLocation;
        this.index = pit.index;
        this.stoneCount = pit.stoneCount;
        this.mancala = pit.mancala;
    }

    /**
     * Author: Brandon Sanchez
     *
     * Returns the location identifier of the pit.
     *
     * @return the pit location as a string
     */
    String getPitLocation() {
        return pitLocation;
    }

    /**
     * Author: Brandon Sanchez
     *
     * Returns the index of the pit.
     *
     * @return the index of the pit
     */
    public int getIndex() {
        return index;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Returns whether the pit is a mancala and for which player.
     *
     * @return 0 if not a mancala, 1 if player 1's mancala, 2 if player 2's mancala
     */
    public int getMancala() {
        return mancala;
    }

    /**
     * Author: Brandon Sanchez
     *
     * Returns the number of stones currently in the pit.
     *
     * @return the stone count
     */
    public int getStoneCount() {
        return stoneCount;
    }

    /**
     * Author: Brandon Sanchez
     *
     * Checks if the pit is empty.
     *
     * @return true if there are no stones in the pit, false otherwise
     */
    public boolean isEmpty() {
        return stoneCount == 0;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Returns the player owning this pit.
     *
     * @return 0 if player 1, 1 if player 2
     */
    public int getPlayer() {
        if (this.index <= 6)
            return 0;
        else return 1;
    }

    /**
     * Author: Brandon Sanchez
     *
     * Sets the number of stones in the pit.
     *
     * @param stoneCount the new stone count
     */
    public void setStoneCount(int stoneCount) {
        this.stoneCount = stoneCount;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Adds a specified number of stones to the pit.
     *
     * @param stones the number of stones to add
     */
    public void addStone(int stones) {
        this.stoneCount += stones;
    }

    /**
     * Author: Eddie Nguyen
     *
     * Creates a copy of this pit.
     *
     * @return a new Pit object with the same attributes
     */
    public Pit copy() {
        return new Pit(this.pitLocation, this.index, this.stoneCount, this.mancala);
    }
}
