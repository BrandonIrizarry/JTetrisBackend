package xyz.brandonirizarry.tetrisboard;

import xyz.brandonirizarry.primitives.Delta;
import xyz.brandonirizarry.primitives.Point;

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
                builder.append(" %2d".formatted(cell));
            }

            builder.append('\n');
        }

        return builder.toString();
    }

    public boolean isTetrominoCell(int rowIndex, int columnIndex) {
        return board[rowIndex][columnIndex] == -1;
    }

    public void drawTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, -1);
    }

    public void eraseTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, 0);
    }

    private void writeTetromino(Point origin, List<Delta> tetromino, int value) {
        board[origin.row()][origin.column()] = value;

        for (var delta : tetromino) {
            var currentPoint = Point.add(origin, delta);
            board[currentPoint.row()][currentPoint.column()] = value;
        }
    }
}
