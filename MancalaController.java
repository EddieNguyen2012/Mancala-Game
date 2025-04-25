package monymancala;

public class MancalaController {

    private MancalaModel model;
    private MancalaView view;
    private GameOver gameOver;
    private int undoCount;
    private int[] board;

    private int currentPlayer;
    public MancalaController(MancalaModel model, MancalaView view) {
        this.model = model;
        this.view = view;
        this.gameOver = new GameOver(model, view, this);

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
    
    
    

    public boolean gameOver() {
        // Danny fill here;
    	boolean playerAstats = true;
    	for (int i = 0; i < 6; i++) {
    		if (board[i] > 0) {
    			playerAstats = false;
    			break;
    		}
    	}
    	
    	boolean playerBstats = true;
    	for (int i = 7; i < 13; i++) {
    		if (board[i] > 0) {
    			playerBstats = false;
    			break;
    		}
    	}
        return playerAstats || playerBstats;
    }
}