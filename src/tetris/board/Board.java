package tetris.board;

import tetris.pieces.Piece;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public final class Board {
    private final int width;
    private final int height;
    private final char[][] cells;
    private static final char DEFAULT_CHAR = '-';
    private static final char PIECE_CHAR = '0';
    // Used to denote a tile of a piece when it has landed, and is therefore frozen.
    // This designation is only internal: when printing, this character is displayed as
    // 'O', as if it were any other tile.
    private static final char FROZEN_CHAR = 'G';

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

        // If any of the drawing template's points is at the bottom row of the board
        // (or has landed on another frozen piece), freeze the piece.
        Predicate<Integer> hasLanded = (point) -> {
            var row = point / width;
            var col = point % width;

            return (row == height - 1) || (cells[row + 1][col] == 'G');
        };

        if (drawingTemplate.stream().anyMatch(hasLanded)) {
            piece.freeze();
        }

        // Update the board according to whether the drawing template specifies
        // it.
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                var linearIndex = col + width * row;

                // Note that, here we avoid accidentally overwriting FROZEN_CHAR cells,
                // since they represent pieces that have landed, and are therefore permanent.
                if (drawingTemplate.contains(linearIndex)) {
                    cells[row][col] = piece.isFrozen() ? FROZEN_CHAR : PIECE_CHAR;
                } else if (cells[row][col] != FROZEN_CHAR) {
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
                // When printing the board, display 'G' characters
                // as 'O', since this is what users (and Hyperskill) expect.
                var ch = cell == 'G' ? 'O' : cell;
                builder.append("%c ".formatted(ch));
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