package com.tictactoe.engine.board;

import com.tictactoe.engine.board.Board.Builder;
import com.tictactoe.engine.pieces.Piece;
import com.tictactoe.engine.pieces.Piece.PieceType;

public class Move {
	private static final Move NULL_MOVE = new NullMove();
	
	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;

	Move(Board board, Piece movedPiece, int destinationCoordinate) {
		super();
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}

	  public Board execute() {
		    final Builder builder = new Builder();
		    
		    for (Piece piece : board.getCurrentPlayer().getActivePieces()) {
				builder.setPiece(piece);
			}
		    
		    for (Piece piece : board.getCurrentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);	
			}
		    
		    //move the moved piece
		    builder.setPiece(movedPiece.movePiece(this));
		    builder.setMoveMaker(board.getCurrentPlayer().getOpponent().getPieceType());
		    
			return builder.build();
   }
	  
	  public static Move createMove(final Board board,
                                    final int destinationCoordinate) {
		  		for(final Move move : board.getAllLegalMoves()) {
		  			if (move.movedPiece.getPieceType()==board.getNextMoveMaker() && move.getDestinationCoordinate()==destinationCoordinate) {
		  				return move;
		  			}
		  		}

		  		return NULL_MOVE;
	  } 

	  public static class NullMove extends Move {
		  public NullMove() {
	         super(null, null, -1);
		  }
		  
		  @Override
		  public Board execute() {
			  throw new RuntimeException("null move!!!");
		  }
	  }	  
	  
	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
	
	public Board getBoard() {
		return this.board;
	}
}
