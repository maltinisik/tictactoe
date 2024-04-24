package com.tictactoe.engine.player.ai;

import com.tictactoe.engine.board.Board;
import com.tictactoe.engine.player.Player;

public class StandartBoardEvaluator implements BoardEvaluator {

	private static final StandartBoardEvaluator INSTANCE = new StandartBoardEvaluator();
	private static final int WIN_BONUS = 1000;
	private static final int DRAW_BONUS = 300;
	
	private StandartBoardEvaluator() {
	}
	
	public static StandartBoardEvaluator get() {
		return INSTANCE;
	}
	
	@Override
	public int evaulate(Board board) {
		return score(board,board.getXPlayer()) - score(board,board.getOPlayer());
	}

	private int score(final Board board, final Player player) {
		return playerWin(player)+playerAboutToWin(player)+playerDraw(board);
	}

	private int playerWin(Player player) {
		if (player.isWin()) return WIN_BONUS;
			
		return 0;
	}
	
	private int playerDraw(Board board) {
		if (board.checkGameOverWithDraw()) return DRAW_BONUS;
			
		return 0;
	}	

	private int playerAboutToWin(Player player) {
		return 0;
	}
}
