package xyz.brandonirizarry.jtetris.board;

import xyz.brandonirizarry.jtetris.circularbuffer.Piece;

public class Board {
    private final GridToken[][] board;
    private Piece currentPiece;

    public Board(int numRows, int numColumns) {
        board = new GridToken[numRows][numColumns];

        for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
            for (int columnIndex = 0; columnIndex < numColumns; columnIndex++) {
                board[rowIndex][columnIndex] = GridToken.Empty;
            }
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

    public void moveDown() {
        this.currentPiece = this.currentPiece.translate(1, 0);
        this.update();
    }

    public void moveLeft() {
        this.currentPiece = this.currentPiece.translate(0, -1);
        this.update();
    }

    private void update() {
        var currentRotation = currentPiece.getFirst();

        for (var rowIndex = 0; rowIndex < board.length; rowIndex++) {
            for (var columnIndex = 0; columnIndex < board[rowIndex].length; columnIndex++) {
                if (currentRotation.hasMember(rowIndex, columnIndex)) {
                    board[rowIndex][columnIndex] = GridToken.Piece;
                } else {
                    board[rowIndex][columnIndex] = GridToken.Empty;
                }
            }
        }
    }
}
