package xyz.brandonirizarry.jtetris.board;

import xyz.brandonirizarry.jtetris.circularbuffer.Piece;

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
        this.currentPiece = this.currentPiece.translate(dr, dc);
        this.update();
    }

    public void moveDown() {
        translateCurrentPiece(1, 0);
    }

    public void moveLeft() {
        translateCurrentPiece(0, -1);
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
