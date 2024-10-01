package xyz.brandonirizarry.tetrisboard;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static xyz.brandonirizarry.TestUtils.checkBoardAgainstFileContents;

public class TetrisBoardTests {
    @Test
    @DisplayName("Empty 6x6 board")
    void confirmAppearanceOfEmpty6x6Board() {
        var tetrisBoard = new TetrisBoard(6, 6);

        checkBoardAgainstFileContents(tetrisBoard, "empty6x6board.txt");
    }
}
