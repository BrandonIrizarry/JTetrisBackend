package tetris.main;

import tetris.pieces.Board;
import tetris.pieces.Piece;
import tetris.pieces.Pieces;

import java.util.*;
import java.util.function.BiFunction;

public class Main {
    final static int X_OFFSET_INITIAL = 3;
    final static int Y_OFFSET_INITIAL = 0;

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        // This function call prompts for the board dimensions.
        var board = getFreshBoard(scanner);
        System.out.println(board);

        // Begin the initial setup: the user at this point can enter
        // "piece" or "exit" only.
        var maybePiece = enterInitialInputState(scanner);

        // User queried "exit", so exit the application.
        if (maybePiece.isEmpty()) {
            return;
        }

        // If not, the user asked for a piece, so initialize it, and then enter the
        // subsequent while-loop.
        var piece = maybePiece.get();
        board.updateState(piece);
        System.out.println(board);

        while (true) {
            // Enter the inner game loop, which lets the user control the piece as it's
            // dropping. This exits when the user either queries "exit" or "piece"
            // (which is only allowed when the current piece is frozen, because it
            // hit ground.)
            //
            // The piece boxed by this Optional is newly allocated.
            maybePiece = enterGameInputState(scanner, piece, board);

            // "exit".
            if (maybePiece.isEmpty()) {
                return;
            }

            // Change the reference to point to the newly allocated piece.
            piece = maybePiece.get();
            board.updateState(piece);
            System.out.println(board);
        }
    }

    private static Piece getFreshPiece(Scanner scanner) {
        final Map<String, BiFunction<Integer, Integer, Piece>> pieceTable = Map.of(
                "I", Pieces.Factory_II,
                "J", Pieces.Factory_JJ,
                "L", Pieces.Factory_LL,
                "O", Pieces.Factory_OO,
                "S", Pieces.Factory_SS,
                "T", Pieces.Factory_TT,
                "Z", Pieces.Factory_ZZ
        );

        var pieceLetter = scanner.nextLine();

        var pieceFactory = pieceTable.get(pieceLetter);
        if (pieceFactory == null) throw new AssertionError();

        return pieceFactory.apply(X_OFFSET_INITIAL, Y_OFFSET_INITIAL);
    }

    private static Board getFreshBoard(Scanner scanner) {
        // We can't simply use 'nextInt', since this doesn't consume the newline. This omission
        // later trips up the 'scanner.nextLine()' invocation when reading game commands.
        List<Integer> boardDimensions = Arrays.stream(scanner.nextLine().split("\\s+"))
                                                .map(Integer::parseInt)
                                                .toList();

        var boardWidth = boardDimensions.get(0);
        var boardHeight = boardDimensions.get(1);

        return new Board(boardHeight, boardWidth);
    }

    // First input state: accept only "piece" and "exit"
    private static Optional<Piece> enterInitialInputState(Scanner scanner) {
        while (true) {
            var command = scanner.nextLine();

            switch(command) {
                case "piece" -> { return Optional.of(getFreshPiece(scanner)); }
                case "exit" -> { return Optional.empty(); }
                default -> { }
            }
        }
    }

    /** The inner game loop for letting the user control a piece as it drops. */
    private static Optional<Piece> enterGameInputState(Scanner scanner, Piece piece, Board board) {
        while (true) {
            var command = scanner.nextLine();

            switch(command) {
                case "rotate" -> piece.rotateCounterclockwise();
                case "left" -> piece.moveLeft();
                case "right" -> piece.moveRight();
                case "down" -> { }
                case "break" -> { } // TODO
                case "exit" -> { return Optional.empty(); }
                case "piece" -> {
                    if (piece.isFrozen()) {
                        return Optional.of(getFreshPiece(scanner));
                    } else {
                        // If the piece isn't frozen yet, "piece" is
                        // considered an invalid command, and therefore
                        // skipped.
                        continue;
                    }
                }
                // If the command isn't valid, don't simply fall through and mistakenly
                // execute the code after this switch statement.
                default -> { continue; }
            }

            piece.moveDown();
            board.updateState(piece);
            System.out.println(board);
        }
    }
}