package xyz.brandonirizarry.matrix;

public class Matrix {
    private final int[][] matrix;

    public Matrix(int numRows, int numColumns) {
        matrix = new int[numRows][numColumns];
    }

    @Override
    public String toString() {
        var builder = new StringBuilder();

        for (var row : matrix) {
            for (var cell : row) {
                builder.append(cell);
            }

            builder.append('\n');
        }

        return builder.toString();
    }
}
