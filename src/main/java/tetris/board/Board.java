package tetris.board;

import tetris.pieces.Piece;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public final class Board {
    private final int width;
    private final int height;
    private final char[][] cells;

    /** Models an ordinary empty tile in the Tetris board. */
    private static final char DEFAULT_CHAR = '-';

    /** Models a tile of a Tetris piece, as it's still falling down. */
    private static final char PIECE_CHAR = '0';

    /** Used to denote a tile of a piece when it has landed, and is therefore frozen.
     * This designation is only internal: when printing, this character is displayed as
     * 'O', as if it were any other tile. */
    private static final char FROZEN_CHAR = 'G';

    /** Models a (temporary) empty space left after clearing a filled horizontal row with
     * the 'break' command. */
    private static final char EMPTY_CHAR = 'E';

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
                var ch = cell == FROZEN_CHAR ? PIECE_CHAR : cell;
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

    /** Update board state by clearing any filled horizontal rows. */
    public void breakHorizontalRows() {
        // First, mark all rows consisting exclusively of 'G' for deletion.
        for (var row : cells) {
            var allFrozenChars = CharBuffer.wrap(row)
                               .chars()
                               .allMatch(ch -> (char)ch == FROZEN_CHAR);

            if (allFrozenChars) {
                Arrays.fill(row, EMPTY_CHAR);
            }

            eliminateEmptyChars();
        }
    }

    /** Clear out all EMPTY_CHAR tiles, causing pieces above to fall in the process.
     * The rules are:
     * 1. If an EMPTY_CHAR is at the top of the board, replace it with DEFAULT_CHAR.
     * 2. If an EMPTY_CHAR is beneath a DEFAULT_CHAR, replace the former with DEFAULT_CHAR.
     * 3. If an EMPTY_CHAR is beneath another EMPTY_CHAR, do nothing.
     * 4. If an EMPTY_CHAR is beneath a FROZEN_CHAR, pull the latter downward by swapping
     *      their positions.
     * 5. Note that it's impossible for an EMPTY_CHAR to be beneath a PIECE_CHAR, since
     *    the PIECE_CHAR would've been frozen by then, and thus converted to a FROZEN_CHAR.
     */
    private void eliminateEmptyChars() {
        while (true) {
            // If we don't find any more EMPTY_CHAR after scanning the board,
            // we exit this while loop.
            boolean foundEmptyChar = false;

            for (int row = 0; row < cells.length; row++) {
                for (int col = 0; col < cells[row].length; col++) {
                    if (cells[row][col] == EMPTY_CHAR) {
                        foundEmptyChar = true;

                        if (row == 0) {
                            cells[row][col] = DEFAULT_CHAR;
                        } else {
                            switch (cells[row - 1][col]) {
                                case DEFAULT_CHAR -> cells[row][col] = DEFAULT_CHAR;
                                case FROZEN_CHAR -> {
                                    var tmp = cells[row - 1][col];
                                    cells[row - 1][col] = cells[row][col];
                                    cells[row][col] = tmp;
                                }
                                case EMPTY_CHAR -> { }
                                default -> throw new IllegalStateException("Illegal char: %c".formatted(cells[row - 1][col]));
                            }
                        }
                    }
                }
            }

            if (!foundEmptyChar) {
                break;
            }
        }
    }

    /** Check whether the board has any column filled to the top with FROZEN_CHAR.
     * If so, return false, to let the caller know that the game is lost.
     * @return Whether a column is filled to the top with FROZEN_CHAR.
     */
    public boolean checkLosingState() {
        for (int col = 0; col < cells[0].length; col++) {
            int countFrozenChars = 0;

            for (var row : cells) {
                if (row[col] == FROZEN_CHAR) {
                    countFrozenChars++;
                }
            }

            if (countFrozenChars == height) {
                return true;
            }
        }

        return false;
    }
}