package com.tictactoe.engine.pieces;

import com.tictactoe.engine.board.Move;

public class PieceX extends Piece {

	PieceX(int piecePosition) {
		super(PieceType.X, piecePosition);
	}

	public String toString() {
		return PieceType.X.getPieceName();
	}

	@Override
	public Piece movePiece(Move move) {
		return new PieceX(move.getDestinationCoordinate());
	}

	@Override
	public PieceType getPieceType() {
		return PieceType.X;
	}
}
