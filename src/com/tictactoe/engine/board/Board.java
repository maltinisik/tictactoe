package com.tictactoe.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.tictactoe.engine.pieces.Piece;
import com.tictactoe.engine.pieces.Piece.PieceType;
import com.tictactoe.engine.player.OPlayer;
import com.tictactoe.engine.player.Player;
import com.tictactoe.engine.player.XPlayer;

public class Board {
	private final List<Tile> gameBoard;
	private final Collection<Piece> xPieces;
	private final Collection<Piece> oPieces;
	
	private final XPlayer xPlayer;
	private final OPlayer oPlayer;
	private final Player currentPlayer;
	private final PieceType nextMoveMaker;
	
	private Board(final Builder builder) {
		this.gameBoard=createGameBoard(builder);
		
		this.xPieces=calculateActivePieces(PieceType.X);
		this.oPieces=calculateActivePieces(PieceType.O);
		
		final Collection<Move> oStandartLegalMoves = calculateLegalMoves(PieceType.O);
		final Collection<Move> xStandartLegalMoves = calculateLegalMoves(PieceType.X);
		
		this.xPlayer = new XPlayer(this,xStandartLegalMoves,oStandartLegalMoves);
		this.oPlayer = new OPlayer(this,oStandartLegalMoves,xStandartLegalMoves);
			
		this.currentPlayer = calculateCurrentPlayer(builder);
		this.nextMoveMaker = calculateNextMoveMaker(builder);
	}
	
    private PieceType calculateNextMoveMaker(Builder builder) {
		return builder.nextMoveMaker;
	}

	private Player calculateCurrentPlayer(Builder builder) {
		return builder.nextMoveMaker.equals(PieceType.X) ? xPlayer : oPlayer;
	}

	private Collection<Move> calculateLegalMoves(PieceType pieceType) {
    	final List<Move> legalMoves= new ArrayList<Move>();
    	for (int tilePosition = 0; tilePosition < BoardUtils.NUM_TILES; tilePosition++) {
			if (!this.getTile(tilePosition).isTileOccupied()) {
				Move move = new Move(this, Piece.createPiece(pieceType, tilePosition), tilePosition);
				legalMoves.add(move);
			}
		}
    	
		return ImmutableList.copyOf(legalMoves);
	}

	public Tile getTile(int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
	}

	private Collection<Piece> calculateActivePieces(PieceType pieceType) {
		final List<Piece> activePieces= new ArrayList<Piece>();
		for (Tile tile : gameBoard) 
		{ 
			if (tile.getPiece()!=null && tile.getPiece().getPieceType()==pieceType) {
				activePieces.add(tile.getPiece());				
			}

		}
		return ImmutableList.copyOf(activePieces);

	}
    
	private static List<Tile> createGameBoard(final Builder builder) {
		final Tile[] tiles = new Tile[BoardUtils.NUM_TILES];

		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = Tile.createTile(i, builder.boardConfig.get(i));
		}
		
		return ImmutableList.copyOf(tiles);
	}

	public Iterable<Move> getAllLegalMoves() {
		return Iterables.unmodifiableIterable(Iterables.concat(this.xPlayer.getLegalMoves(),this.oPlayer.getLegalMoves()));
	}
	
	public static Board createStandardBoard() {
		Builder builder = new Builder();
		builder.setMoveMaker(PieceType.O); 
		
	    return builder.build();	
	}
	
	public static class Builder {
		Map<Integer,Piece> boardConfig;
		PieceType nextMoveMaker;
		
		public Builder() {
			this.boardConfig = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece) {
			this.boardConfig.put(piece.getPiecePosition(),piece);
			return this;
		}
		
		public Builder setMoveMaker(final PieceType nextMoveMaker) {
			this.nextMoveMaker=nextMoveMaker;
			return this;
		}
		
		public Board build() {
			return new Board(this);
		}

	}
	
	public boolean checkGameOverWithDraw() {
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			if (!this.getTile(i).isTileOccupied()) {
				return false;
			} 
		}
		
		return true;
	}
	
	private boolean checkTriple(int index1, int index2, int index3, String pieceName) {
		if (this.getTile(index1).isTileOccupied() && 
			    this.getTile(index2).isTileOccupied() && 
			    this.getTile(index3).isTileOccupied() &&
				this.getTile(index1).getPiece().getPieceType().getPieceName() == pieceName && 
				this.getTile(index2).getPiece().getPieceType().getPieceName() == pieceName && 
				this.getTile(index3).getPiece().getPieceType().getPieceName() == pieceName) {
					return true;
		}
		
		return false;
	}
	
	public boolean checkGameOverWithWin(String pieceName) {

		//check first row all same
		if (checkTriple(0,1,2,pieceName)) {
		   return true;
		}
		
		//check second row all same
		if (checkTriple(3,4,5,pieceName)) {
		   return true;
		}

		//check third row all same
		if (checkTriple(6,7,8,pieceName)) {
		   return true;
		}
		
		//check first column all same
		if (checkTriple(0,3,6,pieceName)) {
		   return true;
		}
		
		//check second column all same
		if (checkTriple(1,4,7,pieceName)) {
		   return true;
		}

		//check third column all same
		if (checkTriple(2,5,8,pieceName)) {
			return true;
		}
		
		//check first diagonal all same
		if (checkTriple(0,4,8,pieceName)) {
			return true;
		}

		//check second diagonal all same
		if (checkTriple(2,4,6,pieceName)) {
			return true;
		}
		
		return false;
	}	
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			final String tileText = prettyPrint(this.gameBoard.get(i));
			sb.append(String.format("%3s", tileText));
			if ((i+1)%BoardUtils.NUM_TILES_PER_ROW == 0) {
				sb.append("\n");				
			}
		}
		
		return sb.toString();
		
	}	

	private static String prettyPrint(Tile tile) {
		return tile.toString();
	}

	public Player getXPlayer() {
		return this.xPlayer;
	}
	
	public Player getOPlayer() {
		return this.oPlayer;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
	public Collection<Piece> getXPieces() {
		return this.xPieces;
	}
	
	public Collection<Piece> getOPieces() {
		return this.oPieces;
	}

	public PieceType getNextMoveMaker() {
		return this.nextMoveMaker;
	}
}
