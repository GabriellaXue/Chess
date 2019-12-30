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

import static com.chess.engine.board.Move.*;

public class Knight extends Piece {

    /**
     * All the possible moves a Knight could have.
     */
    private final static int[] CANDIDATE_MOVE_COORDINATES = {-17, -15, -10, -6, 6, 10, 15, 17};

    /**
     * The constructor of Knight class.
     *
     * @param piecePosition the piece position.
     * @param pieceAlliance the piece color.
     */
    Knight(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Get a list of legal moves of a knight.
     *
     * Check if its destination position is a valid tile coordinate.
     * Then check if this position occupied.
     * If it is, check the alliance color, then decide add to move list or not.
     * If not, add to move list.
     * @param board a given board
     * @return an unspecified list of moves that a knight could possibly reach.
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int currentCandidate : CANDIDATE_MOVE_COORDINATES) {
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidate;
            if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(this.piecePosition, currentCandidate)
                        || isSecondColumnExclusion(this.piecePosition, currentCandidate)
                        || isSeventhColumnExclusion(this.piecePosition, currentCandidate)
                        || isEighthColumnExclusion(this.piecePosition, currentCandidate)) {
                    continue;
                }
                final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                if (!candidateDestinationTile.isTileOccupied()) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    Piece pieceAtDestination = candidateDestinationTile.getPiece();
                    Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
                    if (pieceAtDestinationAlliance != this.pieceAlliance) {
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    /**
     * Include all the exceptional cases in the first column.
     * @param currentPosition the current position.
     * @param candidateOffset the number of steps away from current position.
     * @return true if the destination position(currentPosition + candidateOffset) is not a valid move.
     */
    private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -17 || candidateOffset == -10
                || candidateOffset == 6 || candidateOffset == 15);
    }

    /**
     * Include all the exceptional cases in the second column.
     * @param currentPosition the current position.
     * @param candidateOffset the number of steps away from current position.
     * @return true if the destination position is not a valid move.
     */
    private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.SECOND_COLUMN[currentPosition] && (candidateOffset == -10 || candidateOffset == 6);
    }

    /**
     * Include all the exceptional cases in the seventh column.
     * @param currentPosition the current position.
     * @param candidateOffeset the number of steps away from the current position.
     * @return true if the destination position is not a valid move.
     */
    private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffeset) {
        return BoardUtils.SEVENTH_COLUMN[currentPosition] && (candidateOffeset == -6 || candidateOffeset == 10);
    }

    /**
     * Include all the exceptional cases in the eighth column.
     * @param currentPosition the current position.
     * @param candidateOffset the number of steps away from the current position.
     * @return true if the destination position is not a valid move.
     */
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -15 || candidateOffset == -6
                || candidateOffset == 10 || candidateOffset == 17);
    }
}
