package tetris;

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
        var drawingTemplate = piece.getDrawingTemplate(width);

        if (!validate(drawingTemplate)) {
            piece.undo();
            drawingTemplate = piece.getDrawingTemplate(width);
        }

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

        return true;
    }
}