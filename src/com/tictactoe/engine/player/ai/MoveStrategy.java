package com.tictactoe.engine.player.ai;

import com.tictactoe.engine.board.Board;
import com.tictactoe.engine.board.Move;

public interface MoveStrategy {
  Move execute (Board board);
}
