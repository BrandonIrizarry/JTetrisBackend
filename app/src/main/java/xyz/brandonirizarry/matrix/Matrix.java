package xyz.brandonirizarry.matrix;

import java.util.Arrays;

public class Matrix {
    private final int[][] matrix;

    public Matrix(int numRows, int numColumns) {
        matrix = new int[numRows][numColumns];
        System.out.println(Arrays.deepToString(matrix));
    }
}
