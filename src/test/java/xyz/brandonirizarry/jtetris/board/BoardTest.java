package xyz.brandonirizarry.jtetris.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.Tetromino;
import xyz.brandonirizarry.jtetris.circularbuffer.Piece;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    private final Board board = new Board(21, 12);

    private void checkBoardAgainstFileContents(Board board, String filename) {
        try (var inStream = BoardTest.class
                                    .getClassLoader()
                                    .getResourceAsStream(filename)) {
            assert inStream != null;
            var fileContents = new String(inStream.readAllBytes());
            assertEquals(fileContents, board.toString());
        } catch (IOException e) {
            System.out.println("Nonexistent file");
        }
    }

    @Nested
    @DisplayName("Empty boards")
    class EmptyBoards {
        @Test
        @DisplayName("Check empty 5x6 board printout")
        void checkSmallEmptyBoardDisplay() {
            checkBoardAgainstFileContents(new Board(5, 6), "emptyBoards/emptyBoard5by6.txt");
        }

        @Test
        @DisplayName("Check empty 21x12 board printout")
        void checkEmptyBoardDisplay() {
            checkBoardAgainstFileContents(board, "emptyBoards/emptyBoard21by12.txt");
        }
    }

    @Nested
    @DisplayName("Basic tetromino motions")
    class SimpleMotions {
        private final Piece newPiece = Tetromino.L.getPiece().translate(0, 4);

        @Test
        @DisplayName("Check display after introducing L")
        void checkDisplayAfterIntroducingL() {
            board.introducePiece(newPiece);

            checkBoardAgainstFileContents(BoardTest.this.board, "simpleMotions/boardAfterIntroducingL.txt");
        }

        @Test
        @DisplayName("Check L after moving down once")
        void checkAfterMovingDownOnceL() {
            board.introducePiece(newPiece);
            board.moveDown();

            checkBoardAgainstFileContents(BoardTest.this.board, "simpleMotions/boardL_D1.txt");
        }

        @Test
        @DisplayName("Check L after D1 then L1")
        void checkAfterD1ThenL1() {
            board.introducePiece(newPiece);
            board.moveDown();
            board.moveLeft();

            checkBoardAgainstFileContents(BoardTest.this.board, "simpleMotions/boardL_D1L1.txt");
        }

        @Test
        @DisplayName("Check that valid CCW yields expected position")
        void checkValidCCW() {
            board.introducePiece(newPiece);

            board.rotateCounterclockwise();

            checkBoardAgainstFileContents(BoardTest.this.board, "simpleMotions/boardL_validCCW.txt");
        }
    }

    @Nested
    @DisplayName("Out of bounds collisions")
    class OutOfBounds {
        private final Piece newPiece = Tetromino.L.getPiece().translate(0, 4);

        @Test
        @DisplayName("Check left wall collision")
        void checkLeftWallCollision() {
            board.introducePiece(newPiece);

            for (var i = 0; i < 100; i++) {
                board.moveLeft();
            }

            checkBoardAgainstFileContents(BoardTest.this.board, "outOfBounds/boardL_leftFlush.txt");
        }

        @Test
        @DisplayName("Check right wall collision")
        void checkRightWallCollision() {
            board.introducePiece(newPiece);

            for (var i = 0; i < 100; i++) {
                board.moveRight();
            }

            checkBoardAgainstFileContents(BoardTest.this.board, "outOfBounds/boardL_rightFlush.txt");
        }

        @Test
        @DisplayName("Check that CCW rotation is blocked")
        void checkCCWRotationIsBlocked() {
            board.introducePiece(newPiece);

            for (var i = 0; i < 100; i++) {
                board.moveLeft();
            }

            board.rotateCounterclockwise();

            checkBoardAgainstFileContents(BoardTest.this.board, "outOfBounds/boardL_rotateCCWFlush.txt");
        }
    }

    @Nested
    @DisplayName("Check downward collisions")
    class DownwardCollisions {
        @Test
        @DisplayName("Check simple downward collision of L-piece")
        void checkSimpleDownwardCollisionL() {
            board.introducePiece(Tetromino.L.getPiece().translate(0, 4));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            checkBoardAgainstFileContents(BoardTest.this.board, "downwardCollisions/boardL_floorFlush.txt");
        }

        @Test
        @DisplayName("Check collision of 'O' on top of 'L'")
        void checkOCollidesOnTopOfL() {
            board.introducePiece(Tetromino.L.getPiece().translate(0, 4));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(Tetromino.O.getPiece().translate(0, 4));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            checkBoardAgainstFileContents(board, "downwardCollisions/boardO_fallOnL.txt");
        }

        @Test
        @DisplayName("Check landing of 'O' alongside 'L'")
        void checkOLandsAlongsideL() {
            board.introducePiece(Tetromino.L.getPiece().translate(0, 4));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(Tetromino.O.getPiece());

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            checkBoardAgainstFileContents(board, "downwardCollisions/boardO_fallNextToL.txt");
        }

        @Test
        @DisplayName("Right-side collision doesn't freeze current piece")
        void rightSideCollisionDoesntFreezeCurrentPiece() {
            // Use the same setup as in the previous test: an L is simply dropped on
            // top of an O.
            board.introducePiece(Tetromino.L.getPiece().translate(0, 4));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(Tetromino.O.getPiece().translate(0, 4));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            // Now send a J, and try to ram it from the side against
            // the current stack of dead blocks.
            board.introducePiece(Tetromino.J.getPiece().translate(0, 2));

            for (var i = 0; i < 15; i++) {
                board.moveDown();
            }

            for (var i = 0; i < 100; i++) {
                board.moveRight();
            }

            checkBoardAgainstFileContents(board, "downwardCollisions/boardL_rightFlushDoesntFreeze.txt");
        }
    }

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
            iPiece.rotateCounterclockwise();

            board.introducePiece(iPiece.translate(0, 8));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.introducePiece(iPiece.translate(0, 9));

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
            jPiece.rotateCounterclockwise();
            board.introducePiece(jPiece.translate(0, 1));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            // Note that this is the same rotation.
            board.introducePiece(jPiece.translate(0, 4));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            var tPiece = Tetromino.T.getPiece();
            tPiece.rotateCounterclockwise();
            tPiece.rotateCounterclockwise();
            board.introducePiece(tPiece.translate(0, 6));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            // Get back to the first rotation.
            tPiece.rotateClockwise();
            tPiece.rotateClockwise();

            board.introducePiece(tPiece.translate(0, 8));

            for (var i = 0; i < 100; i++) {
                board.moveDown();
            }

            board.markFilledRowsForDeletion();

            checkBoardAgainstFileContents(board, "markingForDeletion/unevenLineClear.txt");
        }
    }
}
