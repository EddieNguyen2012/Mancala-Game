package edu.sjsu.cs151.monymancala;

public class MancalaController {

    private MancalaModel model;
    private MancalaView view;

    private int undoCount;

    private int currentPlayer;
    public MancalaController(MancalaModel model, MancalaView view) {
        this.model = model;
        this.view = view;

        // Get buttons and link up here
    }

    public void endTurn() {
        undoCount = 0;
        currentPlayer = (currentPlayer + 1) % 2; // Switch player indication
    }

    public void undo() {
        if (undoCount < 3) {
            model.undo();
            undoCount++;
        }
        else {
            view.showErrorMessage("Maximum 3 undo per turn");
        }
    }

    public void gameOver() {
        // Danny fill here;
        return;
    }
}
