package monymancala;
//work in progress

import javax.swing.JOptionPane;


public class GameOver {
	private MancalaModel model;
	private MancalaView view;
	private MancalaController controller;

	
	public GameOver(MancalaModel model, MancalaView view, MancalaController controller) {
		this.model = model;
		this.view = view;
		this.controller = controller;
	}
	
	public void checkGameOver() {
		if (controller.gameOver()) {
			getWinner();
		}
	}
	
	public void getWinner() {
		int[] board = model.getBoard();
		int playerAMancala = board[6];
		int playerAStones = 0;
		int playerBMancala = board[13];
		int playerBStones = 0;
		
		for (int i = 0; i < 6; i++) {
			playerAStones += board[i];
		}
		
		for (int i = 7; i < 13; i++) {
			playerBStones += board[i];
		}
		
		int playerATotal = playerAMancala + playerAStones;
		int playerBTotal = playerBMancala + playerBStones;
		String winner;
		
		if (playerATotal > playerBTotal) {
			winner = "Player A Wins!";
		} else if (playerATotal < playerBTotal) {
			winner = "Player B Wins!";
		} else {
			winner = "It's a Tie!";
		}
		//calls on view to display winner message.
		JOptionPane.showMessageDialog(null, "Game Over! and the results are " + winner);
		view.gameOverMessage(winner);
	}
}

