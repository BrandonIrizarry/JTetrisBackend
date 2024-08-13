package tetris;

import java.util.Arrays;
import java.util.List;
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

        var piece = pieceTable.get(pieceLetter);
        if (piece == null) throw new AssertionError();

        // We can't simply use 'nextInt', since this doesn't consume the newline. This omission
        // later trips up the 'scanner.nextLine()' invocation when reading game commands.
        List<Integer> boardDimensions = Arrays.stream(scanner.nextLine().split("\\s+"))
                                                .map(Integer::parseInt)
                                                .toList();

        var boardWidth = boardDimensions.get(0);
        var boardHeight = boardDimensions.get(1);

        var board = new Board(boardHeight, boardWidth);
        board.updateState(null);
        System.out.println(board);

        piece.setFrameOrigin(3, 0);
        board.updateState(piece);
        System.out.println(board);

        // game command loop
        gameLoop:
        while (true) {
            var command = scanner.nextLine();

            switch(command) {
                case "rotate" -> piece.rotateCounterclockwise();
                case "left" -> {
                    piece.moveLeft();
                }
                case "right" -> {
                    piece.moveRight();
                }
                case "down" -> { }
                case "exit" -> { break gameLoop; }
                default -> throw new IllegalArgumentException("invalid command");
            }

            piece.moveDown();
            board.updateState(piece);
            System.out.println(board);
        }
    }
}