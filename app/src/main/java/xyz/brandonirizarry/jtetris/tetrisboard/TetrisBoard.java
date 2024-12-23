package xyz.brandonirizarry.jtetris.tetrisboard;

import xyz.brandonirizarry.jtetris.game.Cell;
import xyz.brandonirizarry.jtetris.primitives.Delta;
import xyz.brandonirizarry.jtetris.primitives.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TetrisBoard {
    public final int numRows;
    public final int numColumns;

    private Cell[][] board;

    public TetrisBoard(int numRows, int numColumns) {
        board = makeInternalCopy(numRows, numColumns);

        this.numRows = numRows;
        this.numColumns = numColumns;
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (var row : board) {
            for (var cell : row) {
                var content = switch (cell) {
                    case Empty -> '-';
                    case Tetromino -> 'O';
                    case Garbage -> 'X';
                };

                builder.append(" %c".formatted(content));
            }

            builder.append('\n');
        }

        return builder.toString();
    }

    public void drawTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, Cell.Tetromino);
    }

    public void eraseTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, Cell.Empty);
    }

    public void freezeTetromino(Point origin, List<Delta> tetromino) {
        writeTetromino(origin, tetromino, Cell.Garbage);
    }

    public List<Point> findTetromino() {
        List<Point> pieceCells = new ArrayList<>();

        for (var rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            for (var columnIndex = 0; columnIndex < this.numColumns; columnIndex++) {
                if (board[rowIndex][columnIndex] == Cell.Tetromino) {
                    pieceCells.add(new Point(rowIndex, columnIndex));
                }
            }
        }

        return pieceCells;
    }

    public Cell valueAt(Point point) {
        return board[point.row()][point.column()];
    }

    public int collapse() {
        // Clear all filled rows.
        int numLinesCleared = 0;

        for (var row : board) {
            if (isFull(row)) {
                Arrays.fill(row, Cell.Empty);
                numLinesCleared++;
            }
        }

        if (numLinesCleared > 0) {
            // Copy all non-empty rows into a new 2D array, and make this
            // the new 'this.board'.
            var boardCopy = makeInternalCopy(this.numRows, this.numColumns);
            var destinationRowIndex = numRows - 1;

            for (var rowIndex = numRows - 1; rowIndex >= 0; rowIndex--) {
                if (!isEmpty(board[rowIndex])) {
                    System.arraycopy(board[rowIndex], 0, boardCopy[destinationRowIndex], 0, numColumns);
                    destinationRowIndex--;
                }
            }

            board = boardCopy;
        }

        // Report this value to 'moveDown'.
        return numLinesCleared;
    }

    public void clearAll() {
        for (var row : board) {
            Arrays.fill(row, Cell.Empty);
        }
    }

    public Cell[][] export() {
        var copy = new Cell[this.numRows][this.numColumns];

        for (var rowIndex = 0; rowIndex < this.numRows; rowIndex++) {
            System.arraycopy(this.board[rowIndex], 0, copy[rowIndex], 0, this.numColumns);
        }

        return copy;
    }

    private boolean isEmpty(Cell[] row) {
        return Arrays.stream(row).allMatch(x -> x == Cell.Empty);
    }

    private boolean isFull(Cell[] row) {
        return Arrays.stream(row).allMatch(x -> x == Cell.Garbage);
    }

    private void writeTetromino(Point origin, List<Delta> tetromino, Cell value) {
        board[origin.row()][origin.column()] = value;

        for (var delta : tetromino) {
            var currentPoint = Point.add(origin, delta);
            board[currentPoint.row()][currentPoint.column()] = value;
        }
    }

    private Cell[][] makeInternalCopy(int numRows, int numColumns) {
        var copy = new Cell[numRows][numColumns];

        for (var row : copy) {
            Arrays.fill(row, Cell.Empty);
        }

        return copy;
    }
}
