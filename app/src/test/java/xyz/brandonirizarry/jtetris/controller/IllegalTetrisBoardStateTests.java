package xyz.brandonirizarry.jtetris.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.jtetris.tetromino.Tetromino;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IllegalTetrisBoardStateTests {
    private TetrisBoard tetrisBoard;
    private Controller controller;

    @BeforeEach
    void setupController() {
        tetrisBoard = new TetrisBoard(6, 8);
        controller = new Controller(tetrisBoard);
    }

    @Test
    @DisplayName("Prematurely starting another piece throws an exception")
    void prematureStartPiece() {
        controller.startPiece(Tetromino.aliased("O1"));
        controller.moveLeft();
        controller.moveLeft();
        assertThrows(IllegalStateException.class, () -> controller.startPiece(Tetromino.aliased("O1")));
    }
}
