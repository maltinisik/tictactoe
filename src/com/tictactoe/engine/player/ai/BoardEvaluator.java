package com.tictactoe.engine.player.ai;

import com.tictactoe.engine.board.Board;

public interface BoardEvaluator {
	int evaulate(Board board);
    public String evaluationDetails(final Board board);
}