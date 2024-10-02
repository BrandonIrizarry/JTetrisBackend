package xyz.brandonirizarry.tetrisboard;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.primitives.Point;

import java.util.ArrayList;
import java.util.List;

public class TetrisBoard {
    public final int numRows;
    public final int numColumns;

    private final int[][] board;

    public TetrisBoard(int numRows, int numColumns) {
        board = new int[numRows][numColumns];
        this.numRows = numRows;
        this.numColumns = numColumns;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (var row : board) {
            for (var cell : row) {
                builder.append("%2d".formatted(cell));
            }

            builder.append('\n');
        }

        return builder.toString();
    }

    public void drawTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, -1);
    }

    public void eraseTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, 0);
    }

    public void freezeTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, 1);
    }

    public List<Point> findTetromino() {
        List<Point> pieceCells = new ArrayList<>();

        for (var rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            for (var columnIndex = 0; columnIndex < this.numColumns; columnIndex++) {
                if (board[rowIndex][columnIndex] == -1) {
                    pieceCells.add(new Point(rowIndex, columnIndex));
                }
            }
        }

        var size = pieceCells.size();

        // A size of 0 means there is no active tetromino present on the board, which
        // happens for example before a game starts, or else after a piece has landed and
        // no new piece has been introduced yet.
        if (size != 0 && size != 4) {
            throw new IllegalStateException("Illegal polyomino of size %d".formatted(size));
        }

        return pieceCells;
    }

    public int valueAt(Point point) {
        return board[point.row()][point.column()];
    }

    private void writeTetromino(Point origin, List<Delta> tetromino, int value) {
        board[origin.row()][origin.column()] = value;

        for (var delta : tetromino) {
            var currentPoint = Point.add(origin, delta);
            board[currentPoint.row()][currentPoint.column()] = value;
        }
    }
}
