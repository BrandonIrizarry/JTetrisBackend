package xyz.brandonirizarry.tetrisboard;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.primitives.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TetrisBoard {
    public static final int TETROMINO = -1;
    public static final int CLEAR = 0;
    public static final int GARBAGE = 1;

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
        writeTetromino(origin, tetromino, TETROMINO);
    }

    public void eraseTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, CLEAR);
    }

    public void freezeTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, GARBAGE);
    }

    public List<Point> findTetromino() {
        List<Point> pieceCells = new ArrayList<>();

        for (var rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            for (var columnIndex = 0; columnIndex < this.numColumns; columnIndex++) {
                if (board[rowIndex][columnIndex] == TETROMINO) {
                    pieceCells.add(new Point(rowIndex, columnIndex));
                }
            }
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

    public void clear() {
        for (var row : board) {
            Arrays.fill(row, CLEAR);
        }
    }
}
