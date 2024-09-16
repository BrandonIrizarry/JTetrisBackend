package xyz.brandonirizarry.jtetris.board;

public class Board {
    private final GridToken[][] board;

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
}
