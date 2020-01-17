package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class King extends Piece {

    /**
     * All the possible moves a king could have.
     */
    private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};

    /**
     * The constructor of King class.
     *
     * @param piecePosition the piece position.
     * @param pieceAlliance the piece color.
     */
    King(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    @Override
    public Collection<Move> calculateLegalMoves(Board board) {

        final List<Move> legalMoves = new ArrayList<>();

        // normal move
        for (int candidateOffset : CANDIDATE_MOVE_COORDINATE) {
            final int candidateDestinationCoordinate = this.piecePosition + candidateOffset;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isEighthColumnExclusion(candidateDestinationCoordinate, candidateOffset)
                    || isFirstColumnExclusion(candidateDestinationCoordinate, candidateOffset)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Piece attackPiece = candidateDestinationTile.getPiece();
                    final Alliance attackPieceAlliance = attackPiece.getPieceAlliance();
                    if (this.pieceAlliance != attackPieceAlliance) {
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, attackPiece));
                    }
                }
            }
        }

        // castling
        return Collections.unmodifiableList(legalMoves);
    }

    /**
     * Include all the exceptional cases in the first column.
     * @param currentPosition current position.
     * @param candidateOffset the number of steps away from the current position.
     * @return true if the destination position is not a valid move.
     */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1
                || candidateOffset == 7);
    }

    /**
     * Include all the exceptional cases in the eighth column.
     * @param currentPosition current position.
     * @param candidateOffset the number of steps away from the current position.
     * @return true if the destination position is not a valid move.
     */
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1 || candidateOffset == -7
                || candidateOffset == 9);
    }
}
