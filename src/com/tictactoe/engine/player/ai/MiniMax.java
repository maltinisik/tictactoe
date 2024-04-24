package com.tictactoe.engine.player.ai;

import com.tictactoe.engine.player.MoveTransition;
import com.tictactoe.engine.board.Board;
import com.tictactoe.engine.board.Move;

public class MiniMax implements MoveStrategy {

	private BoardEvaluator evaluator;
	private int searchDepth;

	public MiniMax(int searchDepth) {
		evaluator=StandartBoardEvaluator.get();
		this.searchDepth=searchDepth;
	}
	
	@Override
	public String toString() {
		return "MiniMax";
	}
	
    public Move execute(final Board board) {
    	final long startTime = System.currentTimeMillis();
        Move bestMove = null;
        int highestSeenValue = Integer.MIN_VALUE;
        int lowestSeenValue = Integer.MAX_VALUE;
        int currentValue;
        System.out.println(board.getCurrentPlayer() + " THINKING with depth = " +this.searchDepth);
        int moveCounter = 1;
        final int numMoves = board.getCurrentPlayer().getLegalMoves().size();
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                currentValue = board.getCurrentPlayer().getPieceType().isX() ?
                                min(moveTransition.getTransitionBoard(), this.searchDepth - 1) :
                                max(moveTransition.getTransitionBoard(), this.searchDepth - 1);
                if (board.getCurrentPlayer().getPieceType().isX() &&
                        currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (board.getCurrentPlayer().getPieceType().isO() &&
                        currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                    bestMove = move;
                }
            } else {
                System.out.println("\t" + toString() + " can't execute move (" +moveCounter+ "/" +numMoves+ ") " + move);
            }
            moveCounter++;
        }

        return bestMove;
    }

    private int min(final Board board,
                    final int depth) {
        if(depth == 0) {
            return this.evaluator.evaulate(board);
        }
        if(isEndGameScenario(board)) {
            return this.evaluator.evaulate(board);
        }
        int lowestSeenValue = Integer.MAX_VALUE;
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = max(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue <= lowestSeenValue) {
                    lowestSeenValue = currentValue;
                }
            }
        }
        return lowestSeenValue;
    }

    private int max(final Board board,
                    final int depth) {
        if(depth == 0) {
            return this.evaluator.evaulate(board);
        }
        if(isEndGameScenario(board)) {
            return this.evaluator.evaulate(board);
        }
        int highestSeenValue = Integer.MIN_VALUE;
        for (final Move move : board.getCurrentPlayer().getLegalMoves()) {
            final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
            if (moveTransition.getMoveStatus().isDone()) {
                final int currentValue = min(moveTransition.getTransitionBoard(), depth - 1);
                if (currentValue >= highestSeenValue) {
                    highestSeenValue = currentValue;
                }
            }
        }
        return highestSeenValue;
    }


	private boolean isEndGameScenario(Board board) {
		return board.getCurrentPlayer().getOpponent().isWin() || board.checkGameOverWithDraw();
	}
}