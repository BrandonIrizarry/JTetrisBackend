package xyz.brandonirizarry.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.tetrisboard.TetrisBoard;
import xyz.brandonirizarry.tetromino.Tetromino;

import static xyz.brandonirizarry.TestUtils.checkBoardAgainstFileContents;

public class ControllerStartTest {
    @Test
    @DisplayName("J starts with correct position and orientation")
    void checkCorrectPositionAndOrientationJ() {
        var tetrisBoard = new TetrisBoard(6, 6);
        var controller = new Controller(tetrisBoard);

        var J1 = Tetromino.aliased("J1");
        controller.startPiece(J1);

        checkBoardAgainstFileContents(tetrisBoard, "startJ.txt");
    }
}
