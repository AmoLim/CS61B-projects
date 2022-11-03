package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        boolean[][] isMovable = new boolean[30][30];

        switch (side) {
            case NORTH -> board.setViewingPerspective(Side.NORTH);
            case EAST -> board.setViewingPerspective(Side.EAST);
            case WEST -> board.setViewingPerspective(Side.WEST);
            case SOUTH -> board.setViewingPerspective(Side.SOUTH);
        }

        // Side == North
        for (int c = 0; c < board.size(); c++)
            for (int r = board.size() - 1; r >= 0; r--) {
                Tile currentTile = board.tile(c, r);
                int moveToRow = 0;
                boolean isMerge = false;

                // if there exists a non-null tile
                if (currentTile != null) {
                    moveToRow = getFurthestNullTile(c, r);
                    if (moveToRow + 1 < board.size()) {
                        Tile adjacentTile = board.tile(c, moveToRow + 1);

                        if (adjacentTile != null) {
                            int normalizedRow = Side.NORTH.row(c, moveToRow, board.size());
                            int normalizedCol = Side.NORTH.col(c, moveToRow, board.size());
                            if (adjacentTile.value() == currentTile.value()
                                    && !isMovable[normalizedCol][normalizedRow]) {
                                moveToRow++;
                                isMerge = true;
                                isMovable[normalizedCol][normalizedRow] = true;
                            }
                        }
                    }

                    // determine whether the current tile is moved.
                    if (moveToRow != r) {
                            board.move(c, moveToRow, currentTile);
                            changed = true;
                    }
                    // update score if merged
                    score = isMerge? score + 2 * currentTile.value(): score;
                }
            }

        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** Helper method to get the coordinate
     * of the closet null tile of current
     * tile correspond to side.
     * If there is no null tile exist,
     * return the coordinate of current tile.
     */
    private int getFurthestNullTile(int c, int r) {
        for (int i = board.size() - 1; i > r; i--)
            if (board.tile(c, i) == null)
                return i;
        return r;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (int i = 0; i < b.size(); i++)
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null)   // check each tile one by one
                    return true;
            }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int i = 0; i < b.size(); i++)
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) != null) {
                    // if there exists a tile in (i, j), check if its value equals to MAX_PIECE
                    if (b.tile(i, j).value() == MAX_PIECE)
                        return true;
                }
            }

        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {

        // There is at least one empty space on the board.
        if (emptySpaceExists(b))
            return true;


        // There are two adjacent tile with the same value.
        for (int i = 0; i < b.size(); i++)
            for (int j = 0; j < b.size(); j++) {
                // check four adjacent tiles of (i, j)

                int currentTileValue = b.tile(i, j).value();

                //             N  E   S  W
                int [] dRow = {1, 0, -1, 0};
                int [] dCol = {0, 1,  0,-1};

                for (int k = 0; k < 4; k++) {
                    if ((i + dRow[k] >= 0) && (i + dRow[k] < b.size())
                    && (j + dCol[k] >= 0) && (j + dCol[k] < b.size()) ) {
                        // if the corresponding adjacent position is in the board

                        if (b.tile(i + dRow[k], j + dCol[k]) != null)   // check whether there exists a tile
                            if (b.tile(i + dRow[k], j + dCol[k]).value() == currentTileValue)
                                return true;
                    }
                }
            }


        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
