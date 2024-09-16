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
        this.paint();
    }

    private void paint() {
        var currentRotation = currentPiece.getFirst();

        var a = currentRotation.a();
        var b = currentRotation.b();
        var c = currentRotation.c();
        var d = currentRotation.d();

        board[a.row()][a.column()] = GridToken.Piece;
        board[b.row()][b.column()] = GridToken.Piece;
        board[c.row()][c.column()] = GridToken.Piece;
        board[d.row()][d.column()] = GridToken.Piece;
    }
}
