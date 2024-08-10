package tetris;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static final int ROTATION_AREA = 16;

    public static void main(String[] args) {
        Integer[][] O = {{5, 6, 9, 10}};
        Integer[][] I = {{1, 5, 9, 13}, {4, 5, 6, 7}, {1, 5, 9, 13}, {4, 5, 6, 7}};
        Integer[][] S = {{6, 5, 9, 8}, {5, 9, 10, 14}, {6, 5, 9, 8}, {5, 9, 10, 14}};
        Integer[][] Z = {{4, 5, 9, 10}, {2, 5, 6, 9}, {4, 5, 9, 10}, {2, 5, 6, 9}};
        Integer[][] L = {{1, 5, 9, 10}, {2, 4, 5, 6}, {1, 2, 6, 10}, {4, 5, 6, 8}};
        Integer[][] J = {{2, 6, 9, 10}, {4, 5, 6, 10}, {1, 2, 5, 9}, {0, 4, 5, 6}};
        Integer[][] T = {{1, 4, 5, 6}, {1, 4, 5, 9}, {4, 5, 6, 9}, {1, 5, 6, 9}};

        var scanner = new Scanner(System.in);
        String pieceCode = scanner.next();

        var piece = switch(pieceCode) {
            case "O" -> O;
            case "I" -> I;
            case "S" -> S;
            case "Z" -> Z;
            case "L" -> L;
            case "J" -> J;
            case "T" -> T;
            default -> throw new IllegalArgumentException("Invalid code: '%s'".formatted(pieceCode));
        };

        // Here we provide the obligatory printout of all asked-for grid states.
        // To keep things simple at this stage, we simulate an empty grid by providing
        // a rotation array with no data.
        System.out.println(renderRotation(new Integer[]{}));

        for (var rotation : piece) {
            System.out.println(renderRotation(rotation));
        }

        // Since the first rotation of 'O' is also its last rotation, we must avoid printing
        // the corresponding grid twice (the chosen piece's starting grid was already printed
        // out in the above for-loop).
         if (piece != O) {
             System.out.println(renderRotation(piece[0]));
         }
    }

    /** Get a String representation of a grid housing the rotation of a Tetris piece.
     *
     * @param rotation The piece's rotation, represented as an array of int positions.
     * @return A printable String grid of the given rotation.
     */
    private static String renderRotation(Integer[] rotation) {
        var buffer = new String[Main.ROTATION_AREA];

        for (int i = 0; i < buffer.length; i++) {
            String ending;

            if (i % 4 == 3) {
                ending = "\n";
            } else {
                ending = " ";
            }

            buffer[i] = "-" + ending;
        }

        for (int index : rotation) {
            buffer[index] = buffer[index].replace("-", "0");
        }

        return Arrays.stream(buffer).reduce("", (whole, cell) -> whole + cell);
    }
}