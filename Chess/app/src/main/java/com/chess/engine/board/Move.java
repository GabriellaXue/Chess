package com.chess.engine.board;

import com.chess.engine.pieces.Piece;

public abstract class Move {

    /**
     * Board.
     */
    final Board board;

    /**
     * Moved piece.
     */
    final Piece movedPiece;

    /**
     * Destination coordinate.
     */
    final int destinationCoordinate;

    /**
     * The constructor of Class Move.
     * @param board board.
     * @param movedPiece moved piece.
     * @param destinationCoordinate destination coordinate.
     */
    private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
        this.board = board;
        this.movedPiece = movedPiece;
        this.destinationCoordinate = destinationCoordinate;
    }

    /**
     * A subclass of Move called MajorMove.
     * When piece move to an empty tile.
     */
    public static final class MajorMove extends Move{

        /**
         * The constructor of class MajorMove.
         * @param board board.
         * @param movedPiece moved piece.
         * @param destinationCoordinate destination coordinate.
         */
        public MajorMove(final Board board, final Piece movedPiece, final int destinationCoordinate) {
            super(board, movedPiece, destinationCoordinate);
        }
    }

    public static final class AttackMove extends Move {

        /**
         * Attacked piece.
         */
        final Piece attactedPiece;

        /**
         * The constructor of Class Move.
         * @param board board.
         * @param movedPiece moved piece.
         * @param destinationCoordinate destination coordinate.
         * @param attackedPiece attacked piece.
         */
        public AttackMove(Board board, Piece movedPiece, int destinationCoordinate, Piece attackedPiece) {
            super(board, movedPiece, destinationCoordinate);
            this.attactedPiece = attackedPiece;
        }
    }
}
