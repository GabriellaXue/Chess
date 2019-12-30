package com.chess.engine.pieces;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    /**
     * All the possible moves a pawn could have.
     */
    private final static int[] CANDIDATE_MOVE_COORDINATE = {8};

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
    public Collection<Move> calculateLegalMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();
        for (final int candidateOffset : CANDIDATE_MOVE_COORDINATE) {
            // Since the black and white pawn move can only move in the opposite direction,
            // Therefore, we need to consider that black pawn move in the positive direction
            // and white pawn move in the negative direction.
            int candidateDestinationCoordinate = this.piecePosition + this.pieceAlliance.getDirection() * candidateOffset;
            if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
                continue;
            }
            if (candidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
                // TODO more work to do here!!!
                legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
            }
        }
//        final List<Move> legalMoves = new ArrayList<>();
//        int candidateDestinationCoordinate = this.piecePosition;
//        while(BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
//            Tile leftCandidateAttackTile = board.getTile(candidateDestinationCoordinate - 9);
//            Piece leftCandidatePiece = leftCandidateAttackTile.getPiece();
//            Alliance leftCandidateAlliance = leftCandidatePiece.getPieceAlliance();
//            Tile rightCandidateAttackTile = board.getTile(candidateDestinationCoordinate - 7);
//            Piece rightCandidatePiece = rightCandidateAttackTile.getPiece();
//            Alliance rightCandidateAlliance = rightCandidatePiece.getPieceAlliance();
//            // check left attack and right attack.
//            // Then check move forward.
//            if (leftCandidateAttackTile.isTileOccupied() && this.pieceAlliance != leftCandidateAlliance) {
//                legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, leftCandidatePiece))
//            } else if (rightCandidateAttackTile.isTileOccupied() && this.pieceAlliance != rightCandidateAlliance) {
//                legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, rightCandidatePiece));
//            } else {
//                if()
//            }
//
//        }
        return null;
    }

}
