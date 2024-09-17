package xyz.brandonirizarry.jtetris.board;

import org.junit.jupiter.api.*;
import xyz.brandonirizarry.jtetris.JTetris;
import xyz.brandonirizarry.jtetris.Tetromino;
import xyz.brandonirizarry.jtetris.circularbuffer.Piece;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

public class BoardTest {
    private final Board board = new Board(20, 10);

    @Test
    @DisplayName("Check empty 20x10 board printout")
    void checkEmptyBoardDisplay() {
        try (var inStream = JTetris.class
                                            .getClassLoader()
                                            .getResourceAsStream("emptyBoard20by10.txt")) {
            assert inStream != null;
            var fileContents = new String(inStream.readAllBytes());
            assertEquals(fileContents, board.toString());
        } catch (IOException e) {
            System.out.println("Nonexistent file");
        }
    }

    @Nested
    @DisplayName("Check Board motions")
    @TestMethodOrder(OrderAnnotation.class)
    class Motions {
        private final Piece newPiece = Tetromino.L.getPiece().translate(0, 3);

        @Test
        @DisplayName("Check display after introducing L")
        @Order(1)
        void checkDisplayAfterIntroducingL() {
            board.introducePiece(newPiece);

            try (var inStream = JTetris.class
                                        .getClassLoader()
                                        .getResourceAsStream("boardAfterIntroducingL.txt")) {
                assert inStream != null;
                var fileContents = new String(inStream.readAllBytes());
                assertEquals(fileContents, board.toString());
            } catch (IOException e) {
                System.out.println("Nonexistent file");
            }
        }

        @Test
        @DisplayName("Check L after moving down once")
        @Order(2)
        void checkAfterMovingDownOnceL() {
            board.introducePiece(newPiece);
            board.moveDown();

            try (var inStream = JTetris.class
                                        .getClassLoader()
                                        .getResourceAsStream("boardL_D1.txt")) {
                assert inStream != null;
                var fileContents = new String(inStream.readAllBytes());
                assertEquals(fileContents, board.toString());
            } catch (IOException e) {
                System.out.println("Nonexistent file");
            }
        }

        @Test
        @DisplayName("Check L after D1 then L1")
        @Order(3)
        void checkAfterD1ThenL1() {
            board.introducePiece(newPiece);
            board.moveDown();
            board.moveLeft();

            try (var inStream = JTetris.class
                                        .getClassLoader()
                                        .getResourceAsStream("boardL_D1L1.txt")) {
                assert inStream != null;
                var fileContents = new String(inStream.readAllBytes());
                assertEquals(fileContents, board.toString());
            } catch (IOException e) {
                System.out.println("Nonexistent file");
            }
        }
    }
}
