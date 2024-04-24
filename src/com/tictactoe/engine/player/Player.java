package com.tictactoe.engine.player;

import java.util.Collection;

import com.tictactoe.engine.board.Board;
import com.tictactoe.engine.board.Move;
import com.tictactoe.engine.pieces.Piece;
import com.tictactoe.engine.pieces.Piece.PieceType;

public abstract class Player {
	protected final Board board;
	protected final Collection<Move> legalMoves;
	protected final Collection<Move> opponentMoves;
	
	public Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
		this.board=board;
		this.legalMoves=legalMoves;
		this.opponentMoves=opponentMoves;
	}

	public abstract Player getOpponent();

	public abstract Collection<Piece> getActivePieces();

	public abstract PieceType getPieceType();
	
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}

	public Collection<Move> getOpponentMoves() {
		return this.opponentMoves;
	}

	public abstract boolean isWin();

	public MoveTransition makeMove(Move move) {
		if (!isLegal(move)) {
			return new MoveTransition(move.getBoard(), move, MoveStatus.ILLEGAL_MOVE);			
		}
		
		Board transitionBoard = move.execute();
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}

	private boolean isLegal(Move move) {
		return this.legalMoves.contains(move);
	}
}
