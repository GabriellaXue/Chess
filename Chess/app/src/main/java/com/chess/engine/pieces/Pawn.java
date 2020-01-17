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

public class Pawn extends Piece {

    /**
     * All the possible moves a pawn could have.
     */
    private final static int[] CANDIDATE_MOVE_COORDINATE = {7, 8, 9, 16};

    /**
     * The constructor of the Pawn class.
     * @param piecePosition the current piece position.
     * @param pieceAlliance the piece color.
     */
    Pawn(final int piecePosition, final Alliance pieceAlliance) {
        super(piecePosition, pieceAlliance);
    }

    /**
     * Get a list of legal moves of pawn.
     * @param board a given board
     * @return an unspecified list of moves that a pawn could possibly reach.
     */
    @Override
    public Collection<Move> calculateLegalMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateOffset : CANDIDATE_MOVE_COORDINATE) {
            // Since the black and white pawn move can only move in the opposite direction,
            // Therefore, we need to consider that black pawn move in the positive direction
            // and white pawn move in the negative direction.
            final int candidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * candidateOffset;
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            // Handle non attacking pawn move and pawn jump.
            if (candidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                // TODO more work to do here!!!
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            } else if (candidateOffset == 16 && this.isFirstMove() &&
                        ((BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack())
                            || (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()))) {
                final int behindCandidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * 8;
                if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
                        && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
                }
            } else if (candidateOffset == 7 &&
                        !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                            (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Piece attackedPiece = board.getTile(candidateDestinationCoordinate).getPiece();
                    if (this.pieceAlliance != attackedPiece.getPieceAlliance()) {
                        // TODO attacking into a pawn promotion.
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, attackedPiece));
                    }
                }
            } else if (candidateOffset == 9 &&
                        !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                            (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                    final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
                    final Piece attackedPiece = candidateDestinationTile.getPiece();
                    if (this.pieceAlliance != attackedPiece.getPieceAlliance()) {
                        // TODO attacking into a pawn promotion.
                        legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, attackedPiece));
                    }
                }
            }
        }
        // TODO consider the situation that a pawn moves to the last row of the other side and becomes a queen.
        return Collections.unmodifiableList(legalMoves);
    }

}
