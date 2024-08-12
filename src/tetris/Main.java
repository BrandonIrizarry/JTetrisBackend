package tetris;

import java.util.Arrays;
import java.util.Scanner;

final class Board {
    private final int width;
    private final int height;
    private final char[][] cells;
    private static final char DEFAULT_CHAR = '-';
    private static final char PIECE_CHAR = '0';

    public Board(int width, int height) {
        this.cells = new char[width][height];

        // Initialize all cells to '-'
        for (var row : cells) {
            Arrays.fill(row, DEFAULT_CHAR);
        }

        this.width = width;
        this.height = height;
    }

    private void updateState(Piece piece) {
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

    public String getStringRepresentation(Piece piece) {
        updateState(piece);
        var builder = new StringBuilder();

        for (var row : cells) {
            for (var cell : row) {
                builder.append("%c ".formatted(cell));
            }

            builder.append("%n");
        }

        return builder.toString();
    }
}

/** TODO
 *     Allow updateState to accept a null parameter, in which case the drawing template is set to -1, -1, -1, -1,
 *     so that '-' is written everywhere in the board. (We can have that bogus drawing template saved somewhere
 *     as a static field, either in a Utils class, or something similar.)
 */
public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
    }
}