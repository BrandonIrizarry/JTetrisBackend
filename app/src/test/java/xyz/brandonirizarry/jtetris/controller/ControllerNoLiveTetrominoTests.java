package xyz.brandonirizarry.jtetris.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.TestUtils;
import xyz.brandonirizarry.jtetris.tetrisboard.TetrisBoard;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ControllerNoLiveTetrominoTests {
    @Test
    @DisplayName("Controller does nothing when board doesn't have live tetromino")
    void nothingHappensWithoutLiveTetromino() {
        var tetrisBoard = new TetrisBoard(6, 6);
        var controller = new Controller(tetrisBoard);

        assertDoesNotThrow(() -> {
            controller.moveDown();
            controller.moveLeft();
            controller.moveRight();
            controller.rotateCounterclockwise();
            controller.rotateClockwise();
        });

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "controllerNoLiveTetrominoTests/empty6by6Board.txt");
    }
}
