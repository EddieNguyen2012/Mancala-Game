package monymancala;
//work in progress

public class GameOver {
	private MancalaModel mainModel;
	//check gameOver state
	private boolean GameOverStatus;
	
	public GameOver(MancalaModel mainModel) {
		this.mainModel = mainModel;
		this.GameOverStatus = false;
	}
	
	public boolean checkGameOver() {
		boolean APlayerStones = checkPlayerStones(true);
		boolean BPlayerStones = checkPlayerStones(false);
	}
	
	//check if game is over
	public boolean isGameOver() {
		return GameOverStatus;
	}
	
	public void setGameOver(boolean gameOver) {
		this.isGameOver = gameOver;
	}
	
	
	
	// if one or the other side has no more stones then gameOver is set to true.
	if (APlayerStones || BPlayerStones) {
		GameOverStatus = true;
		checkRemainingStones();
		return true;
	}
	return false;
	
	
	// check remaining stones from each player and count them to find the winner.
	public void checkRemainingStones() {
		
	}
	
	public int getWinner() {
		// if the game is not over, set status to false
		if (!isGameOver) {
			return false;
		}
		// get stone count from checkRemainingStones()
		
		if (playerACount > playerBCount) {
			return 1; //player a is the winner
		} else if (playerBCount > playerACount) {
			return 2; //player b is the winner
		} else {
			return 0; //tie
		}
		
	}
}

