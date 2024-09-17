package xyz.brandonirizarry.jtetris.board;

import org.junit.jupiter.api.*;
import xyz.brandonirizarry.jtetris.JTetris;
import xyz.brandonirizarry.jtetris.Tetromino;
import xyz.brandonirizarry.jtetris.circularbuffer.Piece;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

public class BoardTest {
    private final Board board = new Board(21, 12);

    private void checkBoardAgainstFileContents(Board board, String filename) {
        try (var inStream = JTetris.class
                                    .getClassLoader()
                                    .getResourceAsStream(filename)) {
            assert inStream != null;
            var fileContents = new String(inStream.readAllBytes());
            assertEquals(fileContents, board.toString());
        } catch (IOException e) {
            System.out.println("Nonexistent file");
        }
    }

    @Test
    @DisplayName("Check empty 5x6 board printout")
    void checkSmallEmptyBoardDisplay() {
        checkBoardAgainstFileContents(new Board(5, 6), "emptyBoard5by6.txt");
    }

    @Test
    @DisplayName("Check empty 21x12 board printout")
    void checkEmptyBoardDisplay() {
        checkBoardAgainstFileContents(this.board, "emptyBoard21by12.txt");
    }

    @Nested
    @DisplayName("Check Board motions")
    @TestMethodOrder(OrderAnnotation.class)
    class Motions {
        private final Piece newPiece = Tetromino.L.getPiece().translate(0, 4);

        @Test
        @DisplayName("Check display after introducing L")
        @Order(1)
        void checkDisplayAfterIntroducingL() {
            board.introducePiece(newPiece);

            checkBoardAgainstFileContents(BoardTest.this.board, "boardAfterIntroducingL.txt");
        }

        @Test
        @DisplayName("Check L after moving down once")
        @Order(2)
        void checkAfterMovingDownOnceL() {
            board.introducePiece(newPiece);
            board.moveDown();

            checkBoardAgainstFileContents(BoardTest.this.board, "boardL_D1.txt");
        }

        @Test
        @DisplayName("Check L after D1 then L1")
        @Order(3)
        void checkAfterD1ThenL1() {
            board.introducePiece(newPiece);
            board.moveDown();
            board.moveLeft();

            checkBoardAgainstFileContents(BoardTest.this.board, "boardL_D1L1.txt");
        }

        @Test
        @DisplayName("Check left wall collision")
        @Order(4)
        void checkLeftWallCollision() {
            board.introducePiece(newPiece);

            for (var i = 0; i < 100; i++) {
                board.moveLeft();
            }

            checkBoardAgainstFileContents(BoardTest.this.board, "boardL_leftFlush.txt");
        }

        @Test
        @DisplayName("Check right wall collision")
        @Order(5)
        void checkRightWallCollision() {
            board.introducePiece(newPiece);

            for (var i = 0; i < 100; i++) {
                board.moveRight();
            }

            checkBoardAgainstFileContents(BoardTest.this.board, "boardL_rightFlush.txt");
        }
    }
}
