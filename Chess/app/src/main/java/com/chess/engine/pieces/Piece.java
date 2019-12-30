package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

import java.util.Collection;

public abstract class Piece {

    /**
     * The piece position.
     */
    protected final int piecePosition;

    /**
     * The color of the piece.
     */
    protected final Alliance pieceAlliance;

    /**
     * The constructor of Piece class.
     * @param piecePosition the piece position.
     * @param pieceAlliance the piece color.
     */
    Piece(final int piecePosition, final Alliance pieceAlliance) {
        this.pieceAlliance = pieceAlliance;
        this.piecePosition = piecePosition;
    }

    /**
     * Get piece alliance.
     * @return piece alliance.
     */
    public Alliance getPieceAlliance() {
        return this.pieceAlliance;
    }

    /**
     * Get piece position.
     * @return piece position.
     */
    public int getPiecePosition() {
        return this.piecePosition;
    }
    /**
     * Take a given board and for the given piece, it will calculate the piece legal moves.
     * @param board a given board
     * @return a unspecified list of moves that a piece could possibly reach.
     */
    public abstract Collection<Move> calculateLegalMoves(final Board board);

}
