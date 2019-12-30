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

public class Bishop extends Piece {

    /**
     * All the possible directions a bishop could have.
     */
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {-9, -7, 7, 9};

    /**
     * The constructor of Bishop class.
     * @param piecePosition the current piece position.
     * @param pieceAlliance piece alliance.
     */
    Bishop(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);

    }

    /**
     * Get a list of legal moves of a bishop.
     *
     * Bishop goes in diagonal.
     * When it meets a piece, it should stop and couldn't go any further.
     * @param board a given board
     * @return an unspecified list of moves that a bishop could possibly reach.
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE) {
            int candidateDestinationCoordinate = this.piecePosition;
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusive(candidateDestinationCoordinate, candidateCoordinateOffset)
                        || isEighthColumnExclusive(candidateDestinationCoordinate, candidateCoordinateOffset)) {
                    // Break here because if it is one of the exceptional cases, further steps are also beyond the board.
                    // In this way, it will have a shorter run time.
                    break;
                }
                candidateDestinationCoordinate += candidateCoordinateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    // If the tile is not occupied, add a free move.
                    // If it is occupied, add an attack move if it's an enemy piece.
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAtDestinationAlliance) {
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    /**
     * Include all the exceptional cases in the first column.
     * @param currentPosition the current position.
     * @param candidateOffset the candidate offset.
     * @return
     */
    private static boolean isFirstColumnExclusive(final int currentPosition, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7);
    }

    /**
     * Include all the exceptional cases in the eighth column.
     * @param currentPosition the current position.
     * @param candidateOffset the candidate offset.
     * @return true if the destination position(currentPosition + candidateOffset) is not a valid move.
     */
    private static boolean isEighthColumnExclusive(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 9);
    }
}
