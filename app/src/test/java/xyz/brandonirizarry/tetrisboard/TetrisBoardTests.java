package xyz.brandonirizarry.tetrisboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.TestUtils;
import xyz.brandonirizarry.controller.Controller;
import xyz.brandonirizarry.tetromino.Tetromino;

public class TetrisBoardTests {
    @Test
    @DisplayName("Empty 6x6 board")
    void confirmAppearanceOfEmpty6x6Board() {
        var tetrisBoard = new TetrisBoard(6, 6);

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "tetrisBoardTests/empty6x6board.txt");
    }

    @Test
    @DisplayName("Clear a board")
    void boardCleared() {
        var tetrisBoard = new TetrisBoard(6, 6);
        var controller = new Controller(tetrisBoard);

        controller.startPiece(Tetromino.aliased("L1"));
        tetrisBoard.clearAll();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "tetrisBoardTests/empty6x6board.txt");
    }
}
