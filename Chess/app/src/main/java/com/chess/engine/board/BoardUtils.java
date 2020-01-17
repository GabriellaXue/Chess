package com.chess.engine.board;

public class BoardUtils {

    public static final int NUM_TILES = 64;

    public static final int NUM_TILES_PER_ROW = 8;

    /**
     * A list of booleans that only the first column with true value, others are false.
     */
    public static final boolean[] FIRST_COLUMN = initColumn(0);

    /**
     * A list of booleans that only the second column with true value, others are false.
     */
    public static final boolean[] SECOND_COLUMN = initColumn(1);

    /**
     * A list of booleans that only the seventh column with true value, others are false.
     */
    public static final boolean[] SEVENTH_COLUMN = initColumn(6);

    /**
     * A list of booleans that only the eighth column with true value, others are false.
     */
    public static final boolean[] EIGHTH_COLUMN = initColumn(7);

    /**
     * A list of booleans that only the first row with true value, others are false.
     */
    public static final boolean[] FIRST_ROW = initRow(0);

    /**
     * A list of booleans that only the second row with true value, others are false.
     */
    public static final boolean[] SECOND_ROW  = initRow(1);

    /**
     * A list of booleans that only the seventh row with true value, others are false.
     */
    public static final boolean[] SEVENTH_ROW = initRow(6);

    /**
     * A list of booleans that only the eighth row with true value, others are false;
     */
    public static final boolean[] EIGHTH_ROW = initRow(7);

    /**
     * The constructor.
     */
    private BoardUtils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    /**
     * A list of booleans that only the selected row with true value, others are false.
     * @param rowNumber the row number.
     * @return a list of booleans.
     */
    private static boolean[] initRow(int rowNumber) {
        final boolean[] resultRow = new boolean[NUM_TILES];
        int rowNumberOnBoard = NUM_TILES_PER_ROW * rowNumber;
        final int initialPosition = rowNumberOnBoard;
        final int endPoint = initialPosition + NUM_TILES_PER_ROW;
        do {
            resultRow[rowNumberOnBoard] = true;
            rowNumberOnBoard += 1;
        } while (rowNumberOnBoard < endPoint);
        return resultRow;
    }

    /**
     * A list of booleans that only the selected column with true value, others are false.
     * @param columnNumber the column number.
     * @return a list of booleans.
     */
    private static boolean[] initColumn(int columnNumber) {
        final boolean[] resultColumn = new boolean[NUM_TILES];
        do {
            resultColumn[columnNumber] = true;
            columnNumber += NUM_TILES_PER_ROW;
        } while (columnNumber < NUM_TILES);
        return resultColumn;
    }

    /**
     * Check if current tileCoordinate is valid or not.
     * @param tileCoordinate tile position.
     * @return true if it is a valid tile coordinate.
     */
    public static boolean isValidTileCoordinate(final int tileCoordinate) {
        return tileCoordinate >=0 && tileCoordinate < NUM_TILES;
    }
}
