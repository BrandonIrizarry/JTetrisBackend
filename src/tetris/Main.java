package tetris;

import java.util.Map;
import java.util.Scanner;

public class Main {
    final static Map<String, Piece> pieceTable = Map.of(
            "I", Pieces.II,
            "J", Pieces.JJ,
            "L", Pieces.LL,
            "O", Pieces.OO,
            "S", Pieces.SS,
            "T", Pieces.TT,
            "Z", Pieces.ZZ
    );

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var pieceLetter = scanner.nextLine();
        var boardWidth = scanner.nextInt();
        var boardHeight = scanner.nextInt();

        var board = new Board(boardHeight, boardWidth);
        board.updateState(null);
        System.out.println(board);
    }
}