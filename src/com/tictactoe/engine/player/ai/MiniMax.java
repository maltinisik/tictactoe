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
            	/*
                currentValue = board.getCurrentPlayer().getPieceType().isX() ?
                                min(moveTransition.getTransitionBoard(), highestSeenValue,lowestSeenValue,this.searchDepth - 1) :
                                max(moveTransition.getTransitionBoard(), highestSeenValue,lowestSeenValue,this.searchDepth - 1);
                */
                currentValue = board.getCurrentPlayer().getPieceType().isX() ?
                		minimaxAlphaBeta(moveTransition.getTransitionBoard(),this.searchDepth - 1, highestSeenValue,lowestSeenValue,false) :
                	    minimaxAlphaBeta(moveTransition.getTransitionBoard(),this.searchDepth - 1, highestSeenValue,lowestSeenValue,true);
                if (board.getCurrentPlayer().getPieceType().isX() &&
                        currentValue > highestSeenValue) {
                    highestSeenValue = currentValue;
                    bestMove = move;
                } else if (board.getCurrentPlayer().getPieceType().isO() &&
                        currentValue < lowestSeenValue) {
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

    public int minimaxAlphaBeta(final Board board, final int depth, int alpha, int beta, boolean maximizingPlayer) {
    	if (depth == 0 || isEndGameScenario(board)) {
			return this.evaluator.evaulate(board);
	    }
        
        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : board.getCurrentPlayer().getLegalMoves()) {
   		        final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
                int eval = minimaxAlphaBeta(moveTransition.getTransitionBoard(), depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break; // Beta cut-off
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : board.getCurrentPlayer().getLegalMoves()) {
            	final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
                int eval = minimaxAlphaBeta(moveTransition.getTransitionBoard(), depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break; // Alpha cut-off
                }
            }
            return minEval;
        }
    }    
    
    public int max(final Board board,
            final int highest,
            final int lowest,
            final int depth) 
    {
		 if (depth == 0 || isEndGameScenario(board)) {
				return this.evaluator.evaulate(board);
		 }
		 int currentHighest = highest;
		 for (final Move move : (board.getCurrentPlayer().getLegalMoves())) {
		     final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
		     if (moveTransition.getMoveStatus().isDone()) {
		         currentHighest = Math.max(currentHighest, min(moveTransition.getTransitionBoard(),
		        		 currentHighest, lowest,getDepth(depth)));
		         if (lowest <= currentHighest) {
		             break;
		         }
		     }
		 }
		 return currentHighest;
    }

    public int min(final Board board,final int highest, final int lowest,final int depth) {
		if (depth == 0 || isEndGameScenario(board)) {
			return this.evaluator.evaulate(board);
		}
		int currentLowest = lowest;
		for (final Move move : (board.getCurrentPlayer().getLegalMoves())) {
			final MoveTransition moveTransition = board.getCurrentPlayer().makeMove(move);
			if (moveTransition.getMoveStatus().isDone()) {
				currentLowest = Math.min(currentLowest,
						max(moveTransition.getTransitionBoard(), highest, currentLowest, getDepth(depth)));
				if (currentLowest <= highest) {
					break;
				}
			}
		}
		return currentLowest;
    }

   private int getDepth(final int depth ) {
	   return depth -1;
   }

	private boolean isEndGameScenario(Board board) {
		return board.getCurrentPlayer().getOpponent().isWin() || board.checkGameOverWithDraw();
	}
}