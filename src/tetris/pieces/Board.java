package tetris.pieces;

import tetris.pieces.Piece;

import java.util.Arrays;
import java.util.Comparator;
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
        // If the piece can't be moved anymore, there is no point in updating any
        // state, so return.
        if (piece.isFrozen()) {
            return;
        }

        var drawingTemplate = piece.getDrawingTemplate(width);

        // Refetch a drawing template if the current one is invalid, undoing the piece's
        // previous move. Note that we have to perform this code before any freezing, or else
        // the undo mechanism inside this if-block won't take effect.
        if (!validate(drawingTemplate)) {
            piece.undo();
            drawingTemplate = piece.getDrawingTemplate(width);
        }

        // If any of the drawing template's points is at the bottom row of the board,
        // the piece has officially landed, and so can't be moved anymore.
        if (drawingTemplate.stream().anyMatch(point -> point / width == height - 1)) {
            piece.freeze();
        }

        // Update the board according to whether the drawing template specifies
        // it.
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

    private boolean validate(List<Integer> drawingTemplate) {
        // Sort, in case either the original piece data was entered by me in an
        // unsorted manner, or else some algorithm should've previously shuffled
        // the data in the template.
        var sortedTemplate = drawingTemplate
                             .stream()
                             .sorted(Comparator.naturalOrder())
                             .toList();

        // Detect wrapping across the right boundary.
        for (int i = 1; i < sortedTemplate.size(); i++) {
            var previousPoint = sortedTemplate.get(i - 1);
            var currentPoint = sortedTemplate.get(i);

            if (previousPoint % width == width - 1 && currentPoint % width == 0) {
                return false;
            }
        }

        // Detect wrapping across the left boundary.
        // The wrapping is detectable if we iterate across the template backwards.
        for (int i = sortedTemplate.size() - 1; i >= 1; i--) {
            var previousPoint = sortedTemplate.get(i - 1);
            var currentPoint = sortedTemplate.get(i);

            if (previousPoint % width == 0 && currentPoint % width == width - 1) {
                return false;
            }
        }

        // Nothing wrong otherwise.
        return true;
    }
}