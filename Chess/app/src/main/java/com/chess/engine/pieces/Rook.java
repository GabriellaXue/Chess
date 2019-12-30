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

public class Rook extends Piece {

    /**
     * All the possible directions a rook could have.
     */
    private static final int[] CANDIDATE_MOVE_VECTOR_COORDINATE = {8, 1, -1, -8};

    /**
     * The constructor of Rook class.
     *
     * @param piecePosition the piece position.
     * @param pieceAlliance the piece color.
     */
    Rook(int piecePosition, Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Get a list of legal moves of a rook.
     * @param board a given board
     * @return An unspecified list that a rook could possibly reach.
     */
    @Override
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE) {
            int candidateDestinationCoordinate = this.piecePosition;
            // If the coordinate is valid, add the offset.
            // Then if the destination coordinate is valid, determine it is a major move, attack move
            // or neither.
            while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateOffset)
                    || isEighthColumnExclusion(candidateDestinationCoordinate, candidateOffset)) {
                    break;
                }
                candidateDestinationCoordinate += candidateOffset;
                if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                    Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    if (!candidateDestinationTile.isTileOccupied()) {
                        legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                    } else {
                        Piece pieceAtDestination = candidateDestinationTile.getPiece();
                        Alliance pieceAtDestinationAlliance = pieceAtDestination.getPieceAlliance();
                        if (this.pieceAlliance != pieceAtDestinationAlliance) {
                            legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
                        }
                        break;
                    }
                }
            }
        }
        return Collections.unmodifiableList(legalMoves);
    }

    /**
     * Including all the exceptional cases in the first column.
     * @param currentPostion the current position.
     * @param candidateOffset the candidate offset.
     * @return true if the destination position (currentPosition + candidateOffset) is not a valid move.
     */
    private static boolean isFirstColumnExclusion(final int currentPostion, final int candidateOffset) {
        return BoardUtils.FIRST_COLUMN[currentPostion] && (candidateOffset == -1);
    }

    /**
     * Including all the exceptional cases in the eight column.
     * @param currentPosition the current position.
     * @param candidateOffset the candidate offset.
     * @return true if the destination position(currentPosition + candidateOffset) is noa a valid move.
     */
    private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
        return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
    }
}
