package com.tictactoe.engine.player;

import java.util.Collection;

import com.tictactoe.engine.board.Board;
import com.tictactoe.engine.board.Move;
import com.tictactoe.engine.pieces.Piece;
import com.tictactoe.engine.pieces.Piece.PieceType;

public class XPlayer extends Player {

	public XPlayer(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
		super(board, legalMoves, opponentMoves);
	}

	@Override
	public Player getOpponent() {
		return this.board.getOPlayer();
	}

	@Override
	public Collection<Piece> getActivePieces() {
		return this.board.getXPieces();
	}

	@Override
	public PieceType getPieceType() {
		return PieceType.X;
	}

	@Override
	public boolean isWin() {
		return this.board.checkGameOverWithWin(this.getPieceType().getPieceName());
	}
}
