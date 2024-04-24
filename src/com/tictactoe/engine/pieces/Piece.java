package com.tictactoe.engine.pieces;

import com.tictactoe.engine.board.Move;

public abstract class Piece {
  protected final PieceType pieceType;
  protected final int piecePosition;
  
  Piece (final PieceType pieceType, final int piecePosition) {
	  this.pieceType=pieceType;
	  this.piecePosition=piecePosition;
  }
  
  public static Piece createPiece(PieceType pieceType, int piecePosition) {
	  return pieceType==PieceType.O ? new PieceO(piecePosition) : new PieceX(piecePosition);
  }
  
  public enum PieceType {
	  X("X") {
		@Override
		public boolean isX() {
			return true;
		}

		@Override
		public boolean isO() {
			return false;
		}
	} ,O("O") {
		@Override
		public boolean isX() {
			return false;
		}

		@Override
		public boolean isO() {
			return true;
		}
	};

	   private String pieceName;

	   PieceType(final String pieceName) {
		   this.pieceName= pieceName;
	   }
	   
	   public String getPieceName()  {
		   return pieceName;
	   }

	public abstract boolean isX();
	public abstract boolean isO();
  }

  public int getPiecePosition() {
	return this.piecePosition;
  }

  public abstract Piece movePiece(Move move);

  public abstract PieceType getPieceType();
}
