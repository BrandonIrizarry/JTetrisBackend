package xyz.brandonirizarry.jtetris.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.JTetris;
import xyz.brandonirizarry.jtetris.Tetromino;
import xyz.brandonirizarry.jtetris.circularbuffer.Piece;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    @DisplayName("Check display after introducing L")
    void checkDisplayAfterIntroducingL() {
        Piece newPiece = Tetromino.L.getPiece().translate(0, 3);
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
}
