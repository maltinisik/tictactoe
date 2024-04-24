package com.tictactoe.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
import com.tictactoe.engine.pieces.Piece;

public abstract class Tile {
  protected final int tileCoordinate;
  
  private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHES = createAllEmptryTiles();
  
  private Tile (int tileCoordinate) {
	  this.tileCoordinate = tileCoordinate;
  }
  
  public abstract boolean isTileOccupied();
  	 
  public abstract Piece getPiece();
  
  public static final class EmptyTile extends Tile {

	private EmptyTile (int tileCoordinate) {
	  super(tileCoordinate);
	}
	  
	@Override
	public boolean isTileOccupied() {
		return false;
	}

	@Override
	public Piece getPiece() {
		return null;
	}

	@Override
	public String toString() {
		return "-";
	}	
  }

  public static final class OccupiedTile extends Tile {
     Piece pieceOnTile;
	  
	private OccupiedTile (int tileCoordinate, Piece pieceOnTile) {
	  super(tileCoordinate);
	  
	  this.pieceOnTile=pieceOnTile;
	}
	  
	@Override
	public boolean isTileOccupied() {
		return true;
	}

	@Override
	public Piece getPiece() {
		return this.pieceOnTile;
	}

	@Override
	public String toString() {
		return pieceOnTile.toString();
	}
  }

  private static Map<Integer, EmptyTile> createAllEmptryTiles() {
		 
	final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();
	 
	for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
		emptyTileMap.put(i, new EmptyTile(i));
	}
	 
	return ImmutableMap.copyOf(emptyTileMap);
 }
  
  public static Tile createTile(int tileCoordinate, Piece piece) {
	return piece!=null ? new OccupiedTile(tileCoordinate, piece) : new EmptyTile(tileCoordinate);
  }
}
