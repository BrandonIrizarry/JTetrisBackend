package xyz.brandonirizarry.jtetris.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.Tetromino;

import static xyz.brandonirizarry.jtetris.board.BoardTestUtils.checkBoardAgainstFileContents;

public class LineBreakTests {
    private final Board board = new Board(21, 12);

    @Nested
    @DisplayName("Marking for deletion")
    class MarkingForDeletion {
        @Test
        @DisplayName("Marking for deletion after I, I, and O")
        void markingForDeletionAfterIIO() {
            board.introducePiece(Tetromino.I.getPiece().translate(0, 1));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(Tetromino.I.getPiece().translate(0, 5));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(Tetromino.O.getPiece().translate(0, 8));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.markFilledRowsForDeletion();

            checkBoardAgainstFileContents(board, "markingForDeletion/board_singleCompletedRow.txt");
        }

        @Test
        @DisplayName("Marking for deletion after achieving a tetris")
        void markingForDeletionAfterTetris() {
            /*
                We introduce a "dream lineup" of pieces to achieve a tetris:
                eight O's, followed by two I's
             */

            // Introduce two O's, stacked one on top of the other.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece());

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Introduce two more stacked O's to the right.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece().translate(0, 2));

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Introduce two more stacked O's to the right.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece().translate(0, 4));

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Introduce two more stacked O's to the right.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece().translate(0, 6));

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Finally, introduce two vertical I's to complete the tetris.
            var iPiece = Tetromino.I.getPiece();

            board.introducePiece(iPiece.translate(0, 8).rotateCounterclockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(iPiece.translate(0, 9).rotateClockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.markFilledRowsForDeletion();

            checkBoardAgainstFileContents(board, "markingForDeletion/clearTetris.txt");
        }

        @Test
        @DisplayName("Uneven line clear: no game is perfect :)")
        void unevenLineClear() {
            /*
                We introduce a janky lineup that no player would employ purposefully,
                to demonstrate an uneven line break: send two J's, followed by two T's.
             */
            var jPiece = Tetromino.J.getPiece();
            board.introducePiece(jPiece.translate(0, 1).rotateCounterclockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(jPiece.translate(0, 4).rotateCounterclockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            var tPiece = Tetromino.T.getPiece();
            board.introducePiece(tPiece.translate(0, 6).rotateCounterclockwise(2));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(tPiece.translate(0, 8));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.markFilledRowsForDeletion();

            checkBoardAgainstFileContents(board, "markingForDeletion/unevenLineClear.txt");
        }
    }

    @Nested
    @DisplayName("Enforce gravity on cleared cells")
    class EnforceGravity {
        @Test
        @DisplayName("Settling after uneven line clear")
        void settlingAfterUnevenLineClear() {
             /*
                We introduce a janky lineup that no player would employ purposefully,
                to demonstrate an uneven line break: send two J's, followed by two T's.
             */
            var jPiece = Tetromino.J.getPiece();
            board.introducePiece(jPiece.translate(0, 1).rotateCounterclockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(jPiece.translate(0, 4).rotateCounterclockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            var tPiece = Tetromino.T.getPiece();
            board.introducePiece(tPiece.translate(0, 6).rotateCounterclockwise(2));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(tPiece.translate(0, 8));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.markFilledRowsForDeletion();
            board.eliminateClearedChars();

            checkBoardAgainstFileContents(board, "enforceGravity/settleUnevenClear.txt");
        }

        @Test
        @DisplayName("Board empty after tetris")
        void boardIsEmptyAfterTetris() {
         /*
                We introduce a "dream lineup" of pieces to achieve a tetris:
                eight O's, followed by two I's
             */

            // Introduce two O's, stacked one on top of the other.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece());

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Introduce two more stacked O's to the right.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece().translate(0, 2));

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Introduce two more stacked O's to the right.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece().translate(0, 4));

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Introduce two more stacked O's to the right.
            for (var i = 0; i < 2; i++) {
                board.introducePiece(Tetromino.O.getPiece().translate(0, 6));

                for (var j = 0; j < 100; j++) {
                    board.moveDown();
                }
            }

            // Finally, introduce two vertical I's to complete the tetris.
            var iPiece = Tetromino.I.getPiece();

            board.introducePiece(iPiece.translate(0, 8).rotateCounterclockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(iPiece.translate(0, 9).rotateClockwise());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.markFilledRowsForDeletion();
            board.eliminateClearedChars();

            checkBoardAgainstFileContents(board, "enforceGravity/allClearAfterTetris.txt");
        }

        @Test
        @DisplayName("Settling after single completed row")
        void settlingAfterSingleCompletedRow() {
            board.introducePiece(Tetromino.I.getPiece().translate(0, 1));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(Tetromino.I.getPiece().translate(0, 5));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(Tetromino.O.getPiece().translate(0, 8));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.markFilledRowsForDeletion();
            board.eliminateClearedChars();

            checkBoardAgainstFileContents(board, "enforceGravity/simpleClear.txt");
        }
    }
}
