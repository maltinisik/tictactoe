package com.tictactoe.engine.player;

import java.util.Collection;

import com.tictactoe.engine.board.Board;
import com.tictactoe.engine.board.Move;
import com.tictactoe.engine.pieces.Piece;
import com.tictactoe.engine.pieces.Piece.PieceType;

public class OPlayer extends Player {

	public OPlayer(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
		super(board, legalMoves, opponentMoves);
	}

	@Override
	public Player getOpponent() {
		return this.board.getXPlayer();
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getOPieces();
	}

	@Override
	public PieceType getPieceType() {
		return PieceType.O;
	}

	@Override
	public boolean isWin() {
		return this.board.checkGameOverWithWin(this.getPieceType().getPieceName());
	}
}
