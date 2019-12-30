package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Tile {

    /**
     * Interger that represents the tile's position.
     */
    protected final int tileCoordinate;

    /**
     * Create all the empty tiles that are valid up front. Whenever we want to retrieve one,
     * instead of creating a new one, we could simply look it up in the cache.
     */
    private static final Map<Integer, EmptyTile> EMPTY_TILES_CACHE = createAllPossibleEmptyTiles();

    private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < BoardUtils.NUM_TILES; i++) {
            emptyTileMap.put(i, new EmptyTile(i));
        }

        return Collections.unmodifiableMap(emptyTileMap);
    }

    public static Tile creatTile(final int tileCoordinate,final Piece piece) {
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CACHE.get(tileCoordinate);
    }
    /**
     * Constructor of Tile class.
     * @param tileCoordinate the position of the tile.
     */
    private Tile(final int tileCoordinate) {
        this.tileCoordinate = tileCoordinate;
    }

    /**
     * Tell whether the tile is occupied or not.
     * @return True if it is occupied and false if it is empty.
     */
    public abstract boolean isTileOccupied();

    /**
     * Get piece on the selected tile.
     * @return A piece.
     */
    public abstract Piece getPiece();

    /**
     * A subclass of Tile called EmptyTile.
     */
    public static final class EmptyTile extends Tile {

        /**
         * Constructor of EmptyTile class.
         * @param tileCoordinate the position of the tile.
         */
        EmptyTile(final int tileCoordinate) {
            super(tileCoordinate);
        }

        /**
         * Tell whether the selected tile is empty or not.
         * @return false because this is empty tile.
         */
        @Override
        public boolean isTileOccupied() {
            return false;
        }

        /**
         * Get the piece at the selected tile.
         * @return A piece.
         */
        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /**
     * A subclass of Tile called OccupiedTile.
     */
    public static final class OccupiedTile extends Tile {

        /**
         * A piece on the selected tile.
         */
        private final Piece pieceOnTile;

        /**
         * Constructor of OccupiedTile class.
         * @param tileCoordinate tile position.
         * @param pieceOnTile piece on the selected tile.
         */
        private OccupiedTile(final int tileCoordinate, final Piece pieceOnTile) {
            super(tileCoordinate);
            this.pieceOnTile = pieceOnTile;
        }

        /**
         * Tell whether the selected tile is occupied or not.
         * @return true because it is occupied.
         */
        @Override
        public boolean isTileOccupied() {
            return true;
        }

        /**
         * Get the piece on the selected tile.
         * @return a piece on the tile.
         */
        @Override
        public Piece getPiece() {
            return pieceOnTile;
        }
    }
}
