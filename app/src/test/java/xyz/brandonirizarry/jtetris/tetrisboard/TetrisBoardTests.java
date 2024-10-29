package xyz.brandonirizarry.jtetris.tetrisboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.brandonirizarry.jtetris.TestUtils;
import xyz.brandonirizarry.jtetris.primitives.Point;
import xyz.brandonirizarry.jtetris.tetromino.Tetromino;

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

        tetrisBoard.drawTetromino(new Point(0, 3), Tetromino.aliased("J1"));
        tetrisBoard.clearAll();

        TestUtils.checkBoardAgainstFileContents(tetrisBoard, "tetrisBoardTests/empty6x6board.txt");
    }
}
