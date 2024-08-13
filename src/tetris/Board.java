package tetris;

import java.util.Arrays;
import java.util.List;

public final class Board {
    private final int width;
    private final int height;
    private final char[][] cells;
    private static final char DEFAULT_CHAR = '-';
    private static final char PIECE_CHAR = '0';

    public Board(int height, int width) {
        this.cells = new char[height][width];

        // Initialize all cells to '-'
        for (var row : cells) {
            Arrays.fill(row, DEFAULT_CHAR);
        }

        this.height = height;
        this.width = width;
    }

    public void updateState(Piece piece) {
        // If 'piece' is null, use a drawing template that can't appear on the board, so that
        // no 'PIECE_CHAR' is ever written.
        var drawingTemplate = piece.getDrawingTemplate(width);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                var linearIndex = col + width * row;

                if (drawingTemplate.contains(linearIndex)) {
                    cells[row][col] = PIECE_CHAR;
                } else {
                    cells[row][col] = DEFAULT_CHAR;
                }
            }
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (var row : cells) {
            for (var cell : row) {
                builder.append("%c ".formatted(cell));
            }

            builder.append("\n");
        }

        return builder.toString();
    }
}