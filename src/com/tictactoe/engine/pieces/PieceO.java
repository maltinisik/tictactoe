package com.tictactoe.engine.pieces;

import com.tictactoe.engine.board.Move;

public class PieceO extends Piece {

	PieceO(int piecePosition) {
		super(PieceType.O, piecePosition);
	}

	public String toString() {
		return PieceType.O.getPieceName();
	}

	@Override
	public Piece movePiece(Move move) {
		return new PieceO(move.getDestinationCoordinate());
	}

	@Override
	public PieceType getPieceType() {
		return PieceType.O;
	}
}
