package xyz.brandonirizarry.jtetris.board;

import xyz.brandonirizarry.jtetris.circularbuffer.Piece;
import xyz.brandonirizarry.jtetris.recordtypes.Coordinate;

import java.util.List;

public class Board {
    private final GridToken[][] board;
    private Piece currentPiece;

    /** Create the internal Tetris board.<br><br>
     * The board maintains a 2D array of GridTokens which mark how a cell interacts with,
     * or else is used by, the player.<br><br>
     * The dimension parameters to this constructor represent the board's total physical
     * dimensions; that is, they should take into account the two side walls, and the initial
     * ground.
     *
     * @param numRows The total number of physical rows used by the board's internal 2D array.
     * @param numColumns The total number of physical columns used by the board's internal 2D array.
     */
    public Board(int numRows, int numColumns) {
        board = new GridToken[numRows][numColumns];

        // Fill in the "hollow" of the board with the Empty token.
        for (int r = 0; r < numRows - 1; r++) {
            for (int c = 1; c < numColumns - 1 ; c++) {
                board[r][c] = GridToken.Empty;
            }
        }

        // Introduce the side walls.
        for (int r = 0; r < numRows - 1; r++) {
            for (int c : List.of(0, numColumns - 1)) {
                board[r][c] = GridToken.Wall;
            }
        }

        // Introduce the ground.
        for (int c = 0; c < numColumns; c++) {
            board[numRows - 1][c] = GridToken.Ground;
        }
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (var row : board) {
            for (var cell : row) {
                builder.append(cell);
            }

            builder.append('\n');
        }

        return builder.toString();
    }

    public void introducePiece(Piece piece) {
        this.currentPiece = piece;
        this.update();
    }

    private void translateCurrentPiece(int dr, int dc) {
        var candidatePiece = this.currentPiece.translate(dr, dc);

        if (this.verify(candidatePiece)) {
            this.currentPiece = candidatePiece;
            this.update();
        }
    }

    public void moveDown() {
        translateCurrentPiece(1, 0);

        // After having moved down, now check for a downward collision.
        //
        // This is achieved by checking if any cell beneath the piece
        // is a ground cell.  To do this, we first need to isolate the
        // piece's bottom, and check if anything below that is a
        // ground cell.
        var rotation = this.currentPiece.getFirst();
        var bottom = rotation.getBottom();

        // There should only be one row index for every coordinate in 'bottom'.
        assert bottom.stream().map(Coordinate::row).distinct().count() == 1
                : "Uneven bottom: %s".formatted(bottom);

        var nextRowIndex = bottom.getFirst().row() + 1;

        boolean downwardCollision = false;
        var bottomColumnIndices = bottom.stream().map(Coordinate::column).toList();

        for (var columnIndex : bottomColumnIndices) {
            if (board[nextRowIndex][columnIndex] == GridToken.Ground) {
                downwardCollision = true;
                break;
            }
        }

        if (downwardCollision) {
            for (var coordinate : rotation) {
                board[coordinate.row()][coordinate.column()] = GridToken.Ground;
            }
        }
    }

    public void moveLeft() {
        translateCurrentPiece(0, -1);
    }

    public void moveRight() {
        translateCurrentPiece(0, 1);
    }

    public void rotateCounterclockwise() {
        this.currentPiece.rotateCounterclockwise();

        if (this.verify(this.currentPiece)) {
            this.update();
        } else {
            this.currentPiece.rotateClockwise();
        }
    }

    private boolean verify(Piece candidatePiece) {
        var rotation = candidatePiece.getFirst();

        for (var coordinate : rotation) {
            var gridToken = board[coordinate.row()][coordinate.column()];

            if (gridToken.isCollisionToken()) {
                return false;
            }
        }

        return true;
    }

    private void update() {
        var currentRotation = currentPiece.getFirst();

        for (var r = 0; r < board.length; r++) {
            for (var c = 0; c < board[r].length; c++) {
                if (board[r][c] == GridToken.Wall || board[r][c] == GridToken.Ground) continue;

                if (currentRotation.hasMember(r, c)) {
                    board[r][c] = GridToken.Piece;
                } else {
                    board[r][c] = GridToken.Empty;
                }
            }
        }
    }
}
